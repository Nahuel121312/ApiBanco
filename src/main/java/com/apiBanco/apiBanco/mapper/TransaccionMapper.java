package com.apiBanco.apiBanco.mapper;

import com.apiBanco.apiBanco.dtos.transaccion.TransaccionRequestDTO;
import com.apiBanco.apiBanco.dtos.transaccion.TransaccionResponseDTO;
import com.apiBanco.apiBanco.models.Cuenta;
import com.apiBanco.apiBanco.models.Transaccion;
import org.springframework.stereotype.Component;

@Component
public class TransaccionMapper {
    public TransaccionResponseDTO toResponse(Transaccion transaccion){
        TransaccionResponseDTO dto = new TransaccionResponseDTO();

        dto.setIdTransaccion(transaccion.getIdTransaccion());
        dto.setMonto(transaccion.getMonto());
        dto.setDetalle(transaccion.getDetalle());
        dto.setTipoTransaccion(transaccion.getTipoTransaccion());
        dto.setCuentaOrigenId(transaccion.getCuentaOrigen().getIdCuenta());
        dto.setCuentaOrigenId(transaccion.getCuentaDestino().getIdCuenta());

        return dto;
    }

    public Transaccion toEntity(TransaccionRequestDTO dto){
        Transaccion transaccion = new Transaccion();

        transaccion.setTipoTransaccion(dto.getTipoTransaccion());
        transaccion.setMonto(dto.getMonto());
        transaccion.setDetalle(dto.getDetalle());

        Cuenta cuentaOrigen = new Cuenta();
        cuentaOrigen.setIdCuenta(dto.getIdCuentaOrigen());
        transaccion.setCuentaOrigen(cuentaOrigen);

        Cuenta cuentaDestino = new Cuenta();
        cuentaDestino.setIdCuenta(dto.getIdCuentaDestino());
        transaccion.setCuentaDestino(cuentaDestino);

        return transaccion;
    }
}
