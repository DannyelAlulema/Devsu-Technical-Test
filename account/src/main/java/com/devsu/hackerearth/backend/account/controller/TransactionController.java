package com.devsu.hackerearth.backend.account.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsu.hackerearth.backend.account.model.dto.ReportDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@GetMapping
	public ResponseEntity<List<TransactionDto>> getAll() {
		return ResponseEntity.ok(this.transactionService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<TransactionDto> get(@PathVariable Long id) {
		return ResponseEntity.ok(this.transactionService.getById(id));
	}

	@PostMapping
	public ResponseEntity<TransactionDto> create(@Valid @RequestBody TransactionDto transactionDto) {
		transactionDto = this.transactionService.create(transactionDto);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.buildAndExpand(transactionDto.getId())
				.toUri();

		return ResponseEntity.created(location).body(transactionDto);
	}

	@GetMapping("/clients/{clientId}/report")
	public ResponseEntity<ReportDto> report(@PathVariable Long clientId,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTransactionStart,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTransactionEnd) {
		return ResponseEntity
				.ok(this.transactionService.getAllByAccountClientIdAndDateBetween(clientId, dateTransactionStart,
						dateTransactionEnd));
	}
}
