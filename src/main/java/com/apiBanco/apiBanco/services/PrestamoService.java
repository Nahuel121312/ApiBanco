package com.apiBanco.apiBanco.services;

import com.apiBanco.apiBanco.models.Cliente;
import com.apiBanco.apiBanco.models.Cuenta;
import com.apiBanco.apiBanco.models.Prestamo;
import com.apiBanco.apiBanco.models.enums.TipoEstado;
import com.apiBanco.apiBanco.repositories.CuentaRepository;
import com.apiBanco.apiBanco.repositories.PrestamoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final CuentaRepository cuentaRepository;

    public PrestamoService(PrestamoRepository prestamoRepository, CuentaRepository cuentaRepository){
        this.prestamoRepository = prestamoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    //Listar prestamos
    public List<Prestamo> listarPrestamos(){
        return prestamoRepository.findAll();
    }

    //Buscar prestamo
    public Optional<Prestamo> buscarPrestamo(Long id){
        return prestamoRepository.findById(id);
    }

    //Guardar Prestamo
    public Prestamo guardarPrestamo(Prestamo prestamo){
        Cuenta cuenta = cuentaRepository.findByCliente_ClienteId(prestamo.getCliente().getClienteId())
                .orElseThrow(()-> new RuntimeException("Cliente no encontrado"));

        if(prestamo.getTipoEstado() == TipoEstado.APROBADO){
            cuenta.setSaldo(cuenta.getSaldo() + prestamo.getMonto());
        }
        prestamo.setCliente(cuenta.getCliente());
        return prestamoRepository.save(prestamo);
    }

    //Eliminar Prestamo
    public void eliminarPrestamo(Long id){
        prestamoRepository.deleteById(id);
    }

}
