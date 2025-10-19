package com.apiBanco.apiBanco.dtos.tarjeta;

import com.apiBanco.apiBanco.dtos.cuenta.CuentaResponseDTO;
import com.apiBanco.apiBanco.models.enums.TipoTarjeta;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class TarjetaResponseDTO {
    private Long tarjetaId;
    private TipoTarjeta tipoTarjeta;
    private String numeroDeTarjeta;
    private LocalDate fechaVencimiento;
    private double limiteCredito;
    private Long idCuenta;
}
