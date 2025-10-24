package com.apiBanco.apiBanco.services;

import com.apiBanco.apiBanco.dtos.cliente.ClienteRequestDTO;
import com.apiBanco.apiBanco.dtos.cliente.ClienteResponseDTO;
import com.apiBanco.apiBanco.dtos.cuenta.CuentaResponseDTO;
import com.apiBanco.apiBanco.dtos.tarjeta.TarjetaResponseDTO;
import com.apiBanco.apiBanco.mapper.ClienteMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class ClienteManager {
    private final ClienteService clienteService;
    private final CuentaService cuentaService;
    private final TarjetaService tarjetaService;
    private final ClienteMapper clienteMapper;


    @Autowired
    public ClienteManager(ClienteService clienteService, CuentaService cuentaService, TarjetaService tarjetaService, ClienteMapper clienteMapper){
        this.clienteService = clienteService;
        this.cuentaService = cuentaService;
        this.tarjetaService = tarjetaService;
        this.clienteMapper = clienteMapper;
    }

    public ClienteResponseDTO crearClienteConCuentaYTarjera(ClienteRequestDTO clienteRequest){
        log.info("Inicio del proceso de registro del cliente con username:{}", clienteRequest.getUsername());
        //Crear Cliente
        ClienteResponseDTO clienteResponse = clienteService.crearCliente(clienteRequest);

        CuentaResponseDTO cuentaResponse = cuentaService.crearCuenta(clienteResponse.getClienteId());
        TarjetaResponseDTO tarjetaResponse = tarjetaService.crearTarjetaConCuenta(cuentaResponse.getId());

        clienteResponse.setCuentaDto(cuentaResponse);
        cuentaResponse.setListaTarjetasDto(List.of(tarjetaResponse));
        log.info("Registro exitoso para cliente ID:{}", clienteResponse.getClienteId());
        return clienteResponse;
    }
}
