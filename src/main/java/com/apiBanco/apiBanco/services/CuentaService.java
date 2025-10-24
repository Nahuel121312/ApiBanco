package com.apiBanco.apiBanco.services;

import com.apiBanco.apiBanco.dtos.cuenta.CuentaRequestDTO;
import com.apiBanco.apiBanco.dtos.cuenta.CuentaResponseDTO;
import com.apiBanco.apiBanco.mapper.CuentaMapper;
import com.apiBanco.apiBanco.models.Cliente;
import com.apiBanco.apiBanco.models.Cuenta;
import com.apiBanco.apiBanco.models.enums.TipoCuenta;
import com.apiBanco.apiBanco.repositories.ClienteRepository;
import com.apiBanco.apiBanco.repositories.CuentaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
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
    public Page<CuentaResponseDTO> listarCuentas(int page, int size, String numeroDeCuenta){
        Pageable pageable = PageRequest.of(page, size);
        Page<Cuenta> cuentas;
        if (numeroDeCuenta != null && !numeroDeCuenta.isEmpty()) {

            cuentas = cuentaRepository.findByNumeroDeCuentaContainingAndEstadoTrue(numeroDeCuenta, pageable);
        }else {
            cuentas = cuentaRepository.findByEstadoTrue(pageable);
        }
        return cuentas.map(cuentaMapper::toResponseDTO);
    }

    //Buscar por id
    public CuentaResponseDTO buscarCuentaPorId(Long id){
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Cuenta con ID: "+ id +" no encontrado"));
        return cuentaMapper.toResponseDTO(cuenta);
    }

    //Crear Cuenta
    public CuentaResponseDTO crearCuenta(Long id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Cuenta con ID: "+ id +" no encontrado"));

        if(cuentaRepository.existsByCliente_ClienteId(id)){
            throw new RuntimeException("El cliente con ID: "+ id +"ya tiene una cuenta asociada");
        }

        Cuenta cuenta = new Cuenta();
        cuenta.setCliente(cliente);
        cuenta.setNumeroDeCuenta(generarNumeroCuenta());
        cuenta.setTipoCuenta(TipoCuenta.CAJA_AHORRO);
        cuenta.setSaldo(0.0);
        cuenta.setAlias(generarAlias());
        cuenta.setEstado(true);
        cuenta.setFechaApertura(LocalDateTime.now());

        try{
            cuentaRepository.save(cuenta);
            log.info("Cuenta para Cliente con ID: {} creada correctamente", cuenta.getCliente().getClienteId());
        }catch (Exception e){
            log.error("Error al guardar la cuenta para cliente con ID: {}: {}", cuenta.getCliente().getClienteId(), e.getMessage(), e);
            throw new RuntimeException("No se pudo guardar la cuenta");
        }

        return cuentaMapper.toResponseDTO(cuenta);
    }

    //Eliminar Cuenta
    public void eliminarCuenta(Long id){
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Cuenta con ID: "+ id +" no encontrado"));
        cuenta.setEstado(false);
    }

    //Actualizar Cuenta
    public CuentaResponseDTO actualizarCuenta(Long id, CuentaRequestDTO cuentaRequest){
        Cuenta nueva = cuentaRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Cuenta con ID: "+ id +" no encontrado"));

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
