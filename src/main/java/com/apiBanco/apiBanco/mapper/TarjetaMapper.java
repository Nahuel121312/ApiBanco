package com.apiBanco.apiBanco.mapper;

import com.apiBanco.apiBanco.dtos.tarjeta.TarjetaRequestDTO;
import com.apiBanco.apiBanco.dtos.tarjeta.TarjetaResponseDTO;
import com.apiBanco.apiBanco.models.Cuenta;
import com.apiBanco.apiBanco.models.Tarjeta;
import org.springframework.stereotype.Component;

@Component
public class TarjetaMapper {


    public TarjetaResponseDTO toResponse(Tarjeta tarjeta){
        TarjetaResponseDTO dto = new TarjetaResponseDTO();

        dto.setTarjetaId(tarjeta.getIdTarjeta());
        dto.setTipoTarjeta(tarjeta.getTipoTarjeta());
        dto.setIdCuenta(tarjeta.getCuenta().getIdCuenta());
        dto.setFechaVencimiento(tarjeta.getFechaVencimiento());
        dto.setNumeroDeTarjeta(tarjeta.getNumeroDeTarjeta());
        dto.setLimiteCredito(tarjeta.getLimiteCredito());


        return dto;
    }

    public Tarjeta toEntity(TarjetaRequestDTO dto){

        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setTipoTarjeta(dto.getTipoTarjeta());

        Cuenta cuenta = new Cuenta();
        cuenta.setIdCuenta(dto.getCuentaId());
        tarjeta.setCuenta(cuenta);

        return tarjeta;
    }
}
