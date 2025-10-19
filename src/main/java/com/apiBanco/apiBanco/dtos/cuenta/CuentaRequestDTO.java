package com.apiBanco.apiBanco.dtos.cuenta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CuentaRequestDTO {
    @NotBlank(message = "El alias no puede estar vacio")
    private String alias;

    @NotNull(message = "El saldo no puede estar vacio")
    private double saldo;

    @NotNull(message = "El id del cliente no puede estar vacio")
    private Long clienteId;
}
