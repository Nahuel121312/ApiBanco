package com.apiBanco.apiBanco.mapper;

import com.apiBanco.apiBanco.dtos.cuenta.CuentaRequestDTO;
import com.apiBanco.apiBanco.dtos.cuenta.CuentaResponseDTO;
import com.apiBanco.apiBanco.models.Cliente;
import com.apiBanco.apiBanco.models.Cuenta;
import org.springframework.stereotype.Component;

@Component
public class CuentaMapper {

    private final TransaccionMapper transaccionMapper;
    private final TarjetaMapper tarjetaMapper;
    private final PrestamoMapper prestamoMapper;

    public CuentaMapper (TransaccionMapper transaccionMapper, TarjetaMapper tarjetaMapper, PrestamoMapper prestamoMapper){
        this.transaccionMapper = transaccionMapper;
        this.tarjetaMapper = tarjetaMapper;
        this.prestamoMapper = prestamoMapper;
    }

    public CuentaResponseDTO toResponseDTO(Cuenta cuenta){
        CuentaResponseDTO dto = new CuentaResponseDTO();

        dto.setId(cuenta.getIdCuenta());
         dto.setSaldo(cuenta.getSaldo());
         dto.setTipoCuenta(cuenta.getTipoCuenta());
         dto.setNumeroDeCuenta(cuenta.getNumeroDeCuenta());
         dto.setAlias(cuenta.getAlias());
         dto.setIdCuenta(cuenta.getIdCuenta());
         dto.setListaTransaccionesDto(cuenta.getTransacciones().stream()
                 .map(transaccionMapper::toResponse)
                 .toList());
         dto.setListaTarjetasDto(cuenta.getTarjetas().stream()
                 .map(tarjetaMapper::toResponse)
                 .toList());
         dto.setListaPrestamosDto(cuenta.getPrestamos().stream()
                 .map(prestamoMapper::toResponse)
                 .toList());
         return dto;
    }

    public Cuenta toEntity(CuentaRequestDTO cuentaRequest){
        Cuenta cuenta = new Cuenta();

        cuenta.setSaldo(cuenta.getSaldo());
        cuenta.setAlias(cuenta.getAlias());
        Cliente cliente = new Cliente();
        cliente.setClienteId(cuentaRequest.getClienteId());
        cuenta.setCliente(cliente);

        return cuenta;
    }
}
