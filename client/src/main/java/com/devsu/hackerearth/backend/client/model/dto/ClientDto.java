package com.devsu.hackerearth.backend.client.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
	private Long id;

	@NotBlank(message = "DNI es requerido")
	@Pattern(regexp = "\\d{10}(\\d{3})?", message = "DNI debe tener 10 o 13 digitos numéricos")
	private String dni;

	@NotBlank(message = "Nombre es requerido")
	@Size(max = 80, message = "Máximo 80 caracteres")
	private String name;

	@NotBlank
	@Size(min = 8, max = 64, message = "La contraseña debe tener entre 8 y 64 caracteres")
	private String password;

	@NotBlank(message = "Género es requerido")
	@Pattern(regexp = "M|F", message = "Género debe ser M o F")
	private String gender;

	@NotNull(message = "Edad es requerido")
	@Min(value = 18, message = "Debe ser mayor de edad")
	@Max(value = 120, message = "La edad máxima es de 120 años")
	private int age;

	@NotBlank(message = "Dirección es requerida")
	@Size(min = 16, max = 128, message = "La dirección debe tener entre 16 a 128 caracteres")
	private String address;

	@NotBlank
	@Pattern(regexp = "(\\+593|0)9\\d{8}", message = "Debe cumplir el formato +593XXXXXXXX ó 09XXXXXXXX")
	private String phone;

	@NotNull(message = "Activo es requerido")
	private boolean isActive;
}
