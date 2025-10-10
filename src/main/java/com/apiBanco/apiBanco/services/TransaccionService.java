package com.apiBanco.apiBanco.services;

import com.apiBanco.apiBanco.models.Cuenta;
import com.apiBanco.apiBanco.models.Transaccion;
import com.apiBanco.apiBanco.models.enums.TipoTransaccion;
import com.apiBanco.apiBanco.repositories.CuentaRepository;
import com.apiBanco.apiBanco.repositories.TransaccionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final CuentaRepository cuentaRepository;

    public TransaccionService(TransaccionRepository transaccionRepository, CuentaRepository cuentaRepository){
        this.transaccionRepository = transaccionRepository;
        this.cuentaRepository = cuentaRepository;
    }

    //Listar Transacciones
    public List<Transaccion> listarTransacciones(){
        return transaccionRepository.findAll();
    }

    //buscar transaccion por id
    public Optional<Transaccion> obtenerTransaccion(Long id){
        return transaccionRepository.findById(id);
    }

    //Guardar Transaccion
    @Transactional
    public Transaccion guardarTransaccion(Transaccion transaccion){
        //Actualizar saldos
        Cuenta origen = transaccion.getCuentaOrigen();
        Cuenta destino = transaccion.getCuentaDestino();

        if(origen.getSaldo() < transaccion.getMonto()){
            throw new RuntimeException("Saldo insuficiente");
        }


        origen.setSaldo(origen.getSaldo() - transaccion.getMonto());
        destino.setSaldo(destino.getSaldo() + transaccion.getMonto());
        //Guardar cambios en cuentas
        cuentaRepository.save(origen);
        cuentaRepository.save(destino);

        return transaccionRepository.save(transaccion);
    }

    //Eliminar Transaccion
    public void eliminarTransaccion(Long id){
        transaccionRepository.deleteById(id);
    }


}
