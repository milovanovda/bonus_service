## Business level concepts
- User - Owner of a Transaction. For simplicity exists as a column in Transaction only
- Transaction - Record of money transaction of a User
- Bonus Program - Has start date, end date and some rules how bonuses assigned. 
Bonus Programs are independent and can be run in parallel.
Transaction is valid for Bonus Program only if it was created within active period of Bonus Program 
- Bonus Rules - Logic for assigning bonuses. In this case we have only implementation based on thresholds 

## Quick start:
Requires Java 17 to run

Run command 
- *mvn spring-boot:run*

Application will start on port 8080

All exposed rest endpoints are available via link
- http://localhost:8080/swagger-ui/index.html#/

Get list of all users
- http://localhost:8080/transaction/list-users

Get list of transaction of John starting July 1 ending September 30
- http://localhost:8080/transaction/list-user-transactions?userId=John&startDate=2022-07-01&endDate=2022-09-30

Get transaction statistic of John starting July 1 ending September 30
- http://localhost:8080/transaction/list-user-transaction-statistic?userId=John&startDate=2022-07-01&endDate=2022-09-30

Get list of bonus programs
- http://localhost:8080/bonus-program/list

Get bonus statistic of John for bonus program "TestProgramOne"
- http://localhost:8080/bonus-calculator/list-user-bonus-statistic?bonusProgramName=TestProgramOne&userId=John

## Database creation

Liquibase is used to create db schema
Please check db.changelog-master.xml for details

## Initial Data loading

Initial data loaded from CSV file in resource folder.
TransactionPopulationJobConfiguration.java responsible for loading initial data
it uses spring batch to load data. You can read more about it on https://spring.io/guides/gs/batch-processing/

