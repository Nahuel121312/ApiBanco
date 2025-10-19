package com.apiBanco.apiBanco.services;

import com.apiBanco.apiBanco.dtos.cliente.ClienteRequestDTO;
import com.apiBanco.apiBanco.dtos.cliente.ClienteResponseDTO;
import com.apiBanco.apiBanco.mapper.ClienteMapper;
import com.apiBanco.apiBanco.models.Cliente;
import com.apiBanco.apiBanco.models.Cuenta;
import com.apiBanco.apiBanco.models.enums.TipoCuenta;
import com.apiBanco.apiBanco.repositories.ClienteRepository;
import com.apiBanco.apiBanco.repositories.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper){
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    //Listar clientes
    public List<ClienteResponseDTO> listarClientes(){
        return clienteRepository.findByEstadoTrue().stream()
                .map(clienteMapper::toResponseDTO)
                .toList();
    }
    //Buscar por id
    public ClienteResponseDTO obtenerCliente(Long id){
         Cliente cliente = clienteRepository.findById(id)
                 .orElseThrow(()-> new RuntimeException("Cliente no encontrado"));
         return clienteMapper.toResponseDTO(cliente);
    }

    //Crear Cliente
    public ClienteResponseDTO crearCliente(ClienteRequestDTO clienteRequest){
        Cliente cliente = clienteMapper.toEntity(clienteRequest);
        cliente.setFechaRegistro(LocalDateTime.now());
        cliente.setEstado(true);
        clienteRepository.save(cliente);
        return clienteMapper.toResponseDTO(cliente);
    }

    //Actualizar Cliente
    public ClienteResponseDTO actualizarCliente(Long id, ClienteRequestDTO dto){
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cliente no encontrado"));

        clienteExistente.setNombre(dto.getNombre());
        clienteExistente.setApellido(dto.getApellido());
        clienteExistente.setEmail(dto.getEmail());
        clienteExistente.setDni(dto.getDni());
        clienteExistente.setDireccion(dto.getDireccion());
        Cliente actualizado = clienteRepository.save(clienteExistente);

        return clienteMapper.toResponseDTO(actualizado);
    }

    //Eliminar Cliente
    public void eliminarCliente(Long id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cliente no encontrado"));
        cliente.setEstado(false);
        clienteRepository.save(cliente);
    }


}
