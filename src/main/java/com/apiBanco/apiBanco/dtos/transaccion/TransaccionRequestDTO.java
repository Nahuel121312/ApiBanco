package com.apiBanco.apiBanco.dtos.transaccion;

import com.apiBanco.apiBanco.models.enums.TipoTransaccion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransaccionRequestDTO {
    @NotNull(message = "El tipo de transaccion no puede ser nulo")
    private TipoTransaccion tipoTransaccion;

    @NotNull(message = "El monto no puede ser nulo")
    private double monto;

    @NotBlank(message = "El detalle no puede estar vacio")
    private String detalle;

    @NotNull(message = "El id cuentaOrigen no puede ser nulo")
    private Long idCuentaOrigen;

    @NotNull(message = "El id CuentaDestino no puede ser nulo")
    private Long idCuentaDestino;


}
