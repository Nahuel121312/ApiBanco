package com.apiBanco.apiBanco.mapper;

import com.apiBanco.apiBanco.dtos.prestamo.PrestamoRequestDTO;
import com.apiBanco.apiBanco.dtos.prestamo.PrestamoResponseDTO;
import com.apiBanco.apiBanco.models.Cuenta;
import com.apiBanco.apiBanco.models.Prestamo;
import org.springframework.stereotype.Component;

@Component
public class PrestamoMapper {


    public PrestamoResponseDTO toResponse (Prestamo prestamo){
        PrestamoResponseDTO dto = new PrestamoResponseDTO();

        dto.setIdPrestamo(prestamo.getIdPrestamo());
        dto.setMonto(prestamo.getMonto());
        dto.setInteres(prestamo.getInteres());
        dto.setCuotas(prestamo.getCuotas());
        dto.setValorCuotas(prestamo.getValorCuota());
        dto.setCuentaId(prestamo.getCuenta().getIdCuenta());

        return dto;
    }

    public Prestamo toEntity (PrestamoRequestDTO dto){
        Prestamo prestamo = new Prestamo();

        prestamo.setCuotas(dto.getCuotas());
        prestamo.setMonto(dto.getMonto());

        Cuenta cuenta = new Cuenta();
        cuenta.setIdCuenta(dto.getIdCuenta());
        prestamo.setCuenta(cuenta);

        return prestamo;
    }
}
