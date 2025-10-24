package com.apiBanco.apiBanco.services;

import com.apiBanco.apiBanco.dtos.cliente.ClienteRequestDTO;
import com.apiBanco.apiBanco.dtos.cliente.ClienteResponseDTO;
import com.apiBanco.apiBanco.mapper.ClienteMapper;
import com.apiBanco.apiBanco.models.Cliente;
import com.apiBanco.apiBanco.models.enums.Rol;
import com.apiBanco.apiBanco.repositories.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper, PasswordEncoder passwordEncoder){
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
        this.passwordEncoder = passwordEncoder;
    }

    //Listar clientes
    public Page<ClienteResponseDTO> listarClientes(int page, int size, String filtroNombre){

        Pageable pageable = PageRequest.of(page, size);
        Page<Cliente> clientes;
        if(filtroNombre != null && !filtroNombre.isEmpty()){
            clientes = clienteRepository.findByNombreContainingIgnoreCaseAndEstadoTrue(filtroNombre, pageable);
        }else {
            clientes = clienteRepository.findByEstadoTrue(pageable);
        }
        return clientes.map(clienteMapper::toResponseDTO);
    }
    //Buscar por id
    public ClienteResponseDTO obtenerCliente(Long id){
         Cliente cliente = clienteRepository.findById(id)
                 .orElseThrow(()-> new EntityNotFoundException("Cliente con ID: "+ id +" no encontrado"));
         return clienteMapper.toResponseDTO(cliente);
    }

    //Crear Cliente
    public ClienteResponseDTO crearCliente(ClienteRequestDTO clienteRequest){
        log.info("Intentando crear Cliente con username {}", clienteRequest.getUsername());

        Cliente cliente = clienteMapper.toEntity(clienteRequest);
        cliente.setFechaRegistro(LocalDateTime.now());
        cliente.setEstado(true);
        cliente.setPassword(passwordEncoder.encode(clienteRequest.getPassword()));
        cliente.setRol(Rol.ADMIN);

        try {
            clienteRepository.save(cliente);
            log.info("Cliente guardado correctamente, ID: {}", cliente.getClienteId());
        }catch (Exception e){
            log.error("Error al guardar el cliente con ID: {}: {}", cliente.getClienteId(), e.getMessage(), e);
            throw new RuntimeException("No se pudo guardar el cliente");
        }
        return clienteMapper.toResponseDTO(cliente);
    }

    //Actualizar Cliente
    public ClienteResponseDTO actualizarCliente(Long id, ClienteRequestDTO dto){
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Cliente con ID: "+ id +" no encontrado"));

        clienteExistente.setNombre(dto.getNombre());
        clienteExistente.setApellido(dto.getApellido());
        clienteExistente.setEmail(dto.getEmail());
        clienteExistente.setDni(dto.getDni());
        clienteExistente.setDireccion(dto.getDireccion());
        clienteExistente.setUsername(dto.getUsername());

        Cliente actualizado = clienteRepository.save(clienteExistente);

        return clienteMapper.toResponseDTO(actualizado);
    }

    //Eliminar Cliente
    public void eliminarCliente(Long id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Cliente con ID: "+ id +" no encontrado"));
        cliente.setEstado(false);
        clienteRepository.save(cliente);
    }

    //Obtener cliente por username
    public ClienteResponseDTO obtenerClientePorUsername(String username){
        Cliente cliente = clienteRepository.findByUsername(username)
                .orElseThrow(()-> new EntityNotFoundException("Cliente con username"+ username + " no encontrado"));
        return clienteMapper.toResponseDTO(cliente);
    }


}
