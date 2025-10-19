package com.apiBanco.apiBanco.services;

import com.apiBanco.apiBanco.dtos.cliente.ClienteRequestDTO;
import com.apiBanco.apiBanco.dtos.cliente.ClienteResponseDTO;
import com.apiBanco.apiBanco.dtos.cuenta.CuentaResponseDTO;
import com.apiBanco.apiBanco.dtos.tarjeta.TarjetaResponseDTO;
import com.apiBanco.apiBanco.mapper.ClienteMapper;
import com.apiBanco.apiBanco.mapper.CuentaMapper;
import com.apiBanco.apiBanco.mapper.TarjetaMapper;
import com.apiBanco.apiBanco.models.Cliente;
import com.apiBanco.apiBanco.models.Cuenta;
import com.apiBanco.apiBanco.models.Tarjeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        //Crear Cliente
        ClienteResponseDTO clienteResponse = clienteService.crearCliente(clienteRequest);

        CuentaResponseDTO cuentaResponse = cuentaService.crearCuenta(clienteResponse.getClienteId());
        tarjetaService.crearTarjetaConCuenta(cuentaResponse.getId());

        clienteResponse.setCuentaDto(cuentaResponse);
        return clienteResponse;
    }
}
