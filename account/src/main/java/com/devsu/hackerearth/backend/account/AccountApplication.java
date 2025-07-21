package com.devsu.hackerearth.backend.account;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();

		// mapper.createTypeMap(Transaction.class, TransactionDto.class)
		// .addMappings(m -> m.map(
		// src -> src.getAccount().getId(),
		// TransactionDto::setAccountId));

		return mapper;
	}
}
