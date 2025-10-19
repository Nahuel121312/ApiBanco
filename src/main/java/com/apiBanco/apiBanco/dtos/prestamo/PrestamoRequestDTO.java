package com.apiBanco.apiBanco.dtos.prestamo;

import com.apiBanco.apiBanco.models.enums.TipoEstado;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PrestamoRequestDTO {
    @NotNull(message = "El monto no puede ser nulo")
    private double monto;

    @NotNull(message = "Las cuotas no pueden ser nulas")
    @Size(min = 1, max = 12, message = "Las cuotas tienen que ser entre 1 y 12 ")
    private Long cuotas;

    @NotNull(message = "El id no puede ser nulo")
    private Long idCuenta;
}
