package com.apiBanco.apiBanco.services;

import com.apiBanco.apiBanco.models.Cliente;
import com.apiBanco.apiBanco.repositories.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }
    //Listar clientes
    public List<Cliente> listarClientes(){
        return clienteRepository.findAll();
    }
    //Buscar por id
    public Optional<Cliente> obtenerCliente(Long id){
        return clienteRepository.findById(id);
    }

    //Guardar Cliente
    public Cliente guardarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    //Eliminar Cliente
    public void eliminarCliente(Long id){
        clienteRepository.deleteById(id);
    }

}
