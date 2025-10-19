package com.apiBanco.apiBanco.dtos.cliente;

import com.apiBanco.apiBanco.dtos.cuenta.CuentaResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClienteResponseDTO {
    private Long clienteId;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String direccion;
    private CuentaResponseDTO cuentaDto;
}
