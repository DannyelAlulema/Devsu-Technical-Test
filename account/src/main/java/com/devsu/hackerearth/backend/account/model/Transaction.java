package com.devsu.hackerearth.backend.account.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Transaction extends Base {

	private LocalDate date;
	private String type;
	private BigDecimal amount;
	private BigDecimal balance;

	@ManyToOne
	@JoinColumn(name = "account_id", insertable = false, updatable = false)
	private Account account;
}
