package com.apiBanco.apiBanco.services;

import com.apiBanco.apiBanco.dtos.cuenta.CuentaRequestDTO;
import com.apiBanco.apiBanco.dtos.cuenta.CuentaResponseDTO;
import com.apiBanco.apiBanco.mapper.CuentaMapper;
import com.apiBanco.apiBanco.models.Cliente;
import com.apiBanco.apiBanco.models.Cuenta;
import com.apiBanco.apiBanco.models.enums.TipoCuenta;
import com.apiBanco.apiBanco.repositories.ClienteRepository;
import com.apiBanco.apiBanco.repositories.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;
    private final CuentaMapper cuentaMapper;

    public CuentaService(CuentaRepository cuentaRepository, ClienteRepository clienteRepository, CuentaMapper cuentaMapper){
        this.cuentaRepository = cuentaRepository;
        this.clienteRepository = clienteRepository;
        this.cuentaMapper = cuentaMapper;
    }

    //Listar cuentas
    public List<CuentaResponseDTO> listarCuentas(){
        return cuentaRepository.findAll()
                .stream().map(cuentaMapper::toResponseDTO)
                .toList();
    }

    //Buscar por id
    public CuentaResponseDTO buscarCuentaPorId(Long id){
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cuenta no encontrada"));
        return cuentaMapper.toResponseDTO(cuenta);
    }

    //Crear Cuenta
    public CuentaResponseDTO crearCuenta(Long clienteId){
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(()-> new RuntimeException("Cliente no encontrado"));

        Cuenta cuenta = new Cuenta();
        cuenta.setCliente(cliente);
        cuenta.setNumeroDeCuenta(generarNumeroCuenta());
        cuenta.setTipoCuenta(TipoCuenta.CAJA_AHORRO);
        cuenta.setSaldo(0.0);
        cuenta.setAlias(generarAlias());

        cuentaRepository.save(cuenta);

        return cuentaMapper.toResponseDTO(cuenta);
    }

    //Eliminar Cuenta
    public void eliminarCuenta(Long id){
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(()-> new RuntimeException("Cuenta no encontrada"));
        cuenta.setEstado(false);
    }

    //Actualizar Cuenta
    public CuentaResponseDTO actualizarCuenta(Long id, CuentaRequestDTO cuentaRequest){
        Cuenta nueva = cuentaRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        nueva.setSaldo(cuentaRequest.getSaldo());
        nueva.setAlias(cuentaRequest.getAlias());

        Cuenta cuentaActualizada = cuentaRepository.save(nueva);

        return cuentaMapper.toResponseDTO(cuentaActualizada);
    }

    //Generar numero de cuenta aleatorio
    private String generarNumeroCuenta(){
        String numero;
        do{
            numero = String.valueOf((long) (Math.random() * 1_000_000_0000L));
        }while(cuentaRepository.existsByNumeroDeCuenta(numero));

        return numero;
    }

    //Generar Alias
    private String generarAlias(){
        String[] palabras = {"sol", "luna", "rio", "nube","roble", "estrella", "auto"};
        String palabra1 = palabras[new Random().nextInt(palabras.length)];
        String palabra2 = palabras[new Random().nextInt(palabras.length)];
        int numero = new Random().nextInt(9999);

        return palabra1+ "." + palabra2 + "." + numero;
    }

}
