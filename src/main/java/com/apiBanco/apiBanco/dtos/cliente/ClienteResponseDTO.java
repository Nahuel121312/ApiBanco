package com.apiBanco.apiBanco.dtos.cliente;

import com.apiBanco.apiBanco.dtos.cuenta.CuentaResponseDTO;
import com.apiBanco.apiBanco.dtos.tarjeta.TarjetaResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ClienteResponseDTO {
    private Long clienteId;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String direccion;
    private String telefono;
    private CuentaResponseDTO cuentaDto;

    private String username;
}
