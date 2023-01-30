package com.bonus_service.initial_population;

import com.bonus_service.transaction.Transaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class TransactionPopulationJobConfiguration {

    @Bean
    public Job transactionsImportJob(
            JobRepository jobRepository,
            Step johnTransactionImportStep,
            Step maryTransactionImportStep
    ) {

        return new JobBuilder("transactionsImportJob", jobRepository)
                .start(johnTransactionImportStep)
                .next(maryTransactionImportStep)
                .build();
    }

    @Bean
    @JobScope
    public Step johnTransactionImportStep(
            JobRepository jobRepository,
            FieldSetMapper<Transaction> transactionFieldSetMapper,
            ItemWriter<Transaction> transactionWriter,
            PlatformTransactionManager transactionManager) {

        ClassPathResource resource = new ClassPathResource("db/initial-population/john-transactions.csv");

        return new StepBuilder("johnTransactionImportStep", jobRepository)
                .<Transaction, Transaction>chunk(1000, transactionManager)
                .reader(transactionCsvReader(transactionFieldSetMapper, resource))
                .writer(transactionWriter)
                .build();
    }

    @Bean
    @JobScope
    public Step maryTransactionImportStep(
            JobRepository jobRepository,
            ItemWriter<Transaction> transactionWriter,
            FieldSetMapper<Transaction> transactionFieldSetMapper,
            PlatformTransactionManager transactionManager) {

        ClassPathResource resource = new ClassPathResource("db/initial-population/mary-transactions.csv");

        return new StepBuilder("maryTransactionImportStep", jobRepository)
                .<Transaction, Transaction>chunk(1000, transactionManager)
                .reader(transactionCsvReader(transactionFieldSetMapper, resource))
                .writer(transactionWriter)
                .build();
    }

    private FlatFileItemReader<Transaction> transactionCsvReader(FieldSetMapper<Transaction> transactionFieldSetMapper, Resource resource) {
        return new FlatFileItemReaderBuilder<Transaction>()
                .delimited()
                .delimiter(",")
                .names("userId", "amount", "createdAt")
                .resource(resource)
                .fieldSetMapper(transactionFieldSetMapper)
                .linesToSkip(1)
                .saveState(false)
                .build();
    }

    @Bean
    @StepScope
    public ItemWriter<Transaction> transactionWriter(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        JdbcBatchItemWriter<Transaction> writer = new JdbcBatchItemWriter<>();
        writer.setSql("insert into TRANSACTIONS (USER_ID, AMOUNT, CREATED_AT) values (:userId, :amount, :createdAt)");
        writer.setJdbcTemplate(namedParameterJdbcTemplate);
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }

    @Bean
    public ConversionService conversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(String.class, LocalDateTime.class,
                text -> LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return conversionService;
    }

    @Bean
    public FieldSetMapper<Transaction> transactionFieldSetMapper(ConversionService conversionService) {
        BeanWrapperFieldSetMapper<Transaction> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setConversionService(conversionService);
        fieldSetMapper.setTargetType(Transaction.class);
        return fieldSetMapper;
    }

}
