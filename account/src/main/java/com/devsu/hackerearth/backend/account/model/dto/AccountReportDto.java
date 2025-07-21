package com.devsu.hackerearth.backend.account.model.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class AccountReportDto {
    private Long id;
    private String number;
    private BigDecimal initialAmount;
    private BigDecimal balance;
    private List<TransactionReportDto> transactions;
}
