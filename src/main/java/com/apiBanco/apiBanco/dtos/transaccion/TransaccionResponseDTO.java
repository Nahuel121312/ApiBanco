package com.apiBanco.apiBanco.dtos.transaccion;

import com.apiBanco.apiBanco.models.enums.TipoTransaccion;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TransaccionResponseDTO {
    private Long idTransaccion;
    private TipoTransaccion tipoTransaccion;
    private double monto;
    private String detalle;
    private Long cuentaOrigenId;
    private Long cuentaDestinoId;
    private boolean enviada;
}
