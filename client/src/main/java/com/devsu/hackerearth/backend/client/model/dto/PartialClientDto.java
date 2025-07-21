package com.devsu.hackerearth.backend.client.model.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartialClientDto {
    @NotNull(message = "Activo es requerido")
    private boolean isActive;
}
