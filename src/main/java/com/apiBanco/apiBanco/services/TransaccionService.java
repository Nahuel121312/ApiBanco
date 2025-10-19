package com.apiBanco.apiBanco.services;

import com.apiBanco.apiBanco.dtos.transaccion.TransaccionRequestDTO;
import com.apiBanco.apiBanco.dtos.transaccion.TransaccionResponseDTO;
import com.apiBanco.apiBanco.mapper.TransaccionMapper;
import com.apiBanco.apiBanco.models.Cuenta;
import com.apiBanco.apiBanco.models.Transaccion;
import com.apiBanco.apiBanco.models.enums.TipoTransaccion;
import com.apiBanco.apiBanco.repositories.CuentaRepository;
import com.apiBanco.apiBanco.repositories.TransaccionRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.undo.CannotUndoException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final CuentaRepository cuentaRepository;
    private final TransaccionMapper transaccionMapper;

    public TransaccionService(TransaccionRepository transaccionRepository, CuentaRepository cuentaRepository, TransaccionMapper transaccionMapper){
        this.transaccionRepository = transaccionRepository;
        this.cuentaRepository = cuentaRepository;
        this.transaccionMapper = transaccionMapper;
    }

    //Listar Transacciones
    public List<TransaccionResponseDTO> listarTransacciones(){
        return transaccionRepository.existsByEstadoTrue()
                .stream().map(transaccionMapper::toResponse)
                .toList();
    }

    //buscar transaccion por id
    public TransaccionResponseDTO obtenerTransaccion(Long id){
        Transaccion transaccion =  transaccionRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Transaccion no encontrada"));

        return transaccionMapper.toResponse(transaccion);
    }

    //Crear Transaccion
    @Transactional
    public TransaccionResponseDTO crearTransaccion(TransaccionRequestDTO transaccionRequest){

        Cuenta origen = new Cuenta();
        Cuenta destino = new Cuenta();

        origen.setIdCuenta(transaccionRequest.getIdCuentaOrigen());
        destino.setIdCuenta(transaccionRequest.getIdCuentaDestino());

        Transaccion transaccion = transaccionMapper.toEntity(transaccionRequest);
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setCuentaOrigen(origen);
        transaccion.setCuentaDestino(destino);
        transaccion.setEstado(true);

        if(origen.getSaldo() < transaccion.getMonto()){
            throw new RuntimeException("Saldo insuficiente");
        }

        transaccionRepository.save(transaccion);


        return transaccionMapper.toResponse(transaccion);
    }

    //Eliminar Transaccion
    public void eliminarTransaccion(Long id){
        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Transaccion no encontrada"));
        transaccion.setEstado(false);
    }


}
