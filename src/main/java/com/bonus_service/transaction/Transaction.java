package com.bonus_service.transaction;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private Long id;
    private String userId;
    private BigDecimal amount;
    private LocalDateTime createdAt;

}
