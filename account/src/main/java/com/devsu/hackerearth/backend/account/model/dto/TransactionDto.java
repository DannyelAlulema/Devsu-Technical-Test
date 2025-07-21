package com.devsu.hackerearth.backend.account.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
	private Long id;

	@NotNull(message = "La fecha es requerida")
	@PastOrPresent(message = "La fecha no puede ser posterior a la actual")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	@NotBlank(message = "El tipo es requerido")
	@Pattern(regexp = "DEBIT|CREDIT", flags = Pattern.Flag.CASE_INSENSITIVE, message = "El tipo debe ser CREDIT o DEBIT")
	private String type;

	@NotNull(message = "El monto es requerido")
	private BigDecimal amount;

	@Nullable
	private BigDecimal balance;

	@NotNull(message = "La cuenta es requerida")
	@Positive()
	private Long accountId;
}
