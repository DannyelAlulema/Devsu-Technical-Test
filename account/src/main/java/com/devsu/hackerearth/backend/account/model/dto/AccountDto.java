package com.devsu.hackerearth.backend.account.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

	private Long id;
	private String number;
	private String type;
	private BigDecimal initialAmount;
	private BigDecimal balance;
	private boolean isActive;
	private Long clientId;
}
