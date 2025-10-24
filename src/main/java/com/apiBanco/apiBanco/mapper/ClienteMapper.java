package com.apiBanco.apiBanco.mapper;

import com.apiBanco.apiBanco.dtos.cliente.ClienteRequestDTO;
import com.apiBanco.apiBanco.dtos.cliente.ClienteResponseDTO;
import com.apiBanco.apiBanco.models.Cliente;
import com.apiBanco.apiBanco.models.enums.Rol;
import org.springframework.stereotype.Component;


@Component
public class ClienteMapper {

    private final CuentaMapper cuentaMapper;
    public ClienteMapper ( CuentaMapper cuentaMapper){
        this.cuentaMapper = cuentaMapper;}

    public Cliente toEntity(ClienteRequestDTO dto){
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setDni(dto.getDni());
        cliente.setDireccion(dto.getDireccion());
        cliente.setUsername(dto.getUsername());
        cliente.setTelefono(dto.getTelefono());

        if(dto.getRol() == null){
            cliente.setRol(Rol.USER);
        }else {
            cliente.setRol(dto.getRol());
        }

        return cliente;
    }

    public ClienteResponseDTO toResponseDTO(Cliente cliente){
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setClienteId(cliente.getClienteId());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setEmail(cliente.getEmail());
        dto.setDni(cliente.getDni());
        dto.setDireccion(cliente.getDireccion());
        dto.setUsername(cliente.getUsername());
        dto.setTelefono(cliente.getTelefono());

        if(cliente.getCuenta() != null){
            dto.setCuentaDto(cuentaMapper.toResponseDTO(cliente.getCuenta()));
        }
        return dto;
    }
}
