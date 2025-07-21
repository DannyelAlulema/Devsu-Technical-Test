package com.devsu.hackerearth.backend.account.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class TransactionReportDto {
    private Long id;
    private LocalDate date;
    private String type;
    private BigDecimal amount;
    private BigDecimal balance;
}
