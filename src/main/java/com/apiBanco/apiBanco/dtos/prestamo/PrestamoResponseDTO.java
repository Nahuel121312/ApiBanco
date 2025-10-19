package com.apiBanco.apiBanco.dtos.prestamo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PrestamoResponseDTO {
    private Long idPrestamo;
    private double monto;
    private double interes;
    private Long cuotas;
    private double valorCuotas;
    private Long cuentaId;
}
