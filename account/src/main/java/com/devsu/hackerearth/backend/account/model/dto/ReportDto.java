package com.devsu.hackerearth.backend.account.model.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ReportDto {
    private Long clientId;
    private LocalDate startAt;
    private LocalDate endAt;
    private List<AccountReportDto> accounts;
}
