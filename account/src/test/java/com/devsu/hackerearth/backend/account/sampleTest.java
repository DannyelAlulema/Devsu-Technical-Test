package com.devsu.hackerearth.backend.account;

import java.math.BigDecimal;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;

import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class sampleTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private Long existingId;

	@BeforeAll
	void setup() throws Exception {
		AccountDto account = new AccountDto(
				null,
				"001",
				"SAVINGS",
				new BigDecimal("1000.00"),
				new BigDecimal("1000.00"),
				true,
				7L);

		MvcResult response = mockMvc.perform(post("/api/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(account)))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", containsString("/api/accounts")))
				.andReturn();

		String body = response.getResponse().getContentAsString();
		AccountDto created = objectMapper.readValue(body, AccountDto.class);
		existingId = created.getId();
	}

	@Test
	void whenGetAll_thenReturnNonEmptyList() throws Exception {
		mockMvc.perform(get("/api/accounts"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", not(empty())))
				.andExpect(jsonPath("$[0].id", notNullValue()));
	}

	@Test
	void whenGetById_thenReturnCorrectAccount() throws Exception {
		mockMvc.perform(get("/api/accounts/{id}", existingId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(existingId))
				.andExpect(jsonPath("$.number").value("001"))
				.andExpect(jsonPath("$.balance").value(1000.00));
	}

	@Test
	void whenPutUpdate_thenAccountIsReplaced() throws Exception {
		AccountDto update = new AccountDto(
				null,
				"001",
				"CHECKING",
				new BigDecimal("500.00"),
				new BigDecimal("500.00"),
				false,
				7L);

		mockMvc.perform(put("/api/accounts/{id}", existingId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(update)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(existingId))
				.andExpect(jsonPath("$.type").value("CHECKING"))
				.andExpect(jsonPath("$.balance").value(500.00))
				.andExpect(jsonPath("$.active").value(false));
	}

	@Test
	void whenPathcUpdateIsActive_thenOnlyIsActiveChanges() throws Exception {
		PartialAccountDto patch = new PartialAccountDto(false);

		mockMvc.perform(patch("/api/accounts/{id}", existingId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(patch)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(existingId))
				.andExpect(jsonPath("$.active").value(false));
	}

	@Test
	void whenDelete_thenSubsecuentGetReturns404() throws Exception {
		mockMvc.perform(delete("/api/accounts/{id}", existingId))
				.andExpect(status().isNoContent());
	}
}
