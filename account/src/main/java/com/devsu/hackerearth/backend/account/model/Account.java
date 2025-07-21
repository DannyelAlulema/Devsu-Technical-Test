package com.devsu.hackerearth.backend.account.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

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
public class Account extends Base {
    private String number;
    private String type;
    private BigDecimal balance;
    private BigDecimal initialAmount;
    private boolean isActive;

    @Column(name = "client_id")
    private Long clientId;
}
