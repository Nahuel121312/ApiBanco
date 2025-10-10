package com.apiBanco.apiBanco.services;

import com.apiBanco.apiBanco.models.Cuenta;
import com.apiBanco.apiBanco.repositories.CuentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository){
        this.cuentaRepository = cuentaRepository;
    }

    //Listar cuentas
    public List<Cuenta> listarCuentas(){
        return cuentaRepository.findAll();
    }

    //Buscar por id
    public Optional<Cuenta> buscarCuentaPorId(Long id){
        return cuentaRepository.findById(id);
    }

    //Guardar Cuenta
    public Cuenta guardarCuenta(Cuenta cuenta){
        return cuentaRepository.save(cuenta);
    }

    //Eliminar Cuenta
    public void eliminarCuenta(Long id){
        cuentaRepository.deleteById(id);
    }


}
