package com.apiBanco.apiBanco.dtos.cuenta;

import com.apiBanco.apiBanco.dtos.prestamo.PrestamoRequestDTO;
import com.apiBanco.apiBanco.dtos.prestamo.PrestamoResponseDTO;
import com.apiBanco.apiBanco.dtos.tarjeta.TarjetaResponseDTO;
import com.apiBanco.apiBanco.dtos.transaccion.TransaccionResponseDTO;
import com.apiBanco.apiBanco.models.enums.TipoCuenta;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CuentaResponseDTO {
    private Long id;
    private TipoCuenta tipoCuenta;
    private String numeroDeCuenta;
    private String alias;
    private double saldo;

    private List<PrestamoResponseDTO> listaPrestamosDto;
    private List<TarjetaResponseDTO> listaTarjetasDto;
    private List<TransaccionResponseDTO> listaTransaccionesDto;
}
