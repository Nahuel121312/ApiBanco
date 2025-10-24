package com.apiBanco.apiBanco.dtos.tarjeta;

import com.apiBanco.apiBanco.models.enums.TipoTarjeta;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TarjetaRequestDTO {
    @NotNull(message = "El id no puede ser nulo")
    private Long cuentaId;

    @NotNull(message = "El tipo de tarjeta no puede ser nulo")
    private TipoTarjeta tipoTarjeta;


}
