package com.apiBanco.apiBanco.services;

import com.apiBanco.apiBanco.dtos.transaccion.TransaccionRequestDTO;
import com.apiBanco.apiBanco.dtos.transaccion.TransaccionResponseDTO;
import com.apiBanco.apiBanco.mapper.TransaccionMapper;
import com.apiBanco.apiBanco.models.Cuenta;
import com.apiBanco.apiBanco.models.Transaccion;
import com.apiBanco.apiBanco.models.enums.TipoTransaccion;
import com.apiBanco.apiBanco.repositories.CuentaRepository;
import com.apiBanco.apiBanco.repositories.TransaccionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<TransaccionResponseDTO> listarTransacciones(int page , int size, TipoTransaccion tipoTransaccion){
        Pageable pageable = PageRequest.of(page, size);

        Page<Transaccion> listaTransaccion;
        if(tipoTransaccion != null){
            listaTransaccion = transaccionRepository.findByTipoTransaccionAndEstadoTrue(tipoTransaccion, pageable);
        }else {
            listaTransaccion = transaccionRepository.findByEstadoTrue(pageable);
        }

        return listaTransaccion.map(transaccionMapper::toResponse);
    }

    //buscar transaccion por id
    public TransaccionResponseDTO obtenerTransaccion(Long id){
        Transaccion transaccion =  transaccionRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Transaccion con ID:"+ id +" no encontrada"));

        return transaccionMapper.toResponse(transaccion);
    }

    //Crear Transaccion
    @Transactional
    public TransaccionResponseDTO crearTransaccion(TransaccionRequestDTO transaccionRequest){

        Cuenta origen = cuentaRepository.findById(transaccionRequest.getIdCuentaOrigen())
                .orElseThrow(()->new EntityNotFoundException("Cuenta con ID:"+ transaccionRequest.getIdCuentaOrigen() +" no encontrada"));
        Cuenta destino = cuentaRepository.findById(transaccionRequest.getIdCuentaDestino())
                .orElseThrow(()-> new EntityNotFoundException("Cuenta con ID:"+ transaccionRequest.getIdCuentaDestino() +" no encontrada"));


        Transaccion transaccion = transaccionMapper.toEntity(transaccionRequest);

        if(origen.getSaldo() < transaccion.getMonto()){
            throw new RuntimeException("Saldo insuficiente");
        }else {
            origen.setSaldo(origen.getSaldo() - transaccion.getMonto());
            destino.setSaldo(destino.getSaldo() + transaccion.getMonto());
            transaccion.setEnviada(true);
        }
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setCuentaOrigen(origen);
        transaccion.setCuentaDestino(destino);
        transaccion.setEstado(true);


        transaccionRepository.save(transaccion);


        return transaccionMapper.toResponse(transaccion);
    }

    //Eliminar Transaccion
    public void eliminarTransaccion(Long id){
        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Transaccion con ID:"+ id +" no encontrada"));
        transaccion.setEstado(false);
        transaccionRepository.save(transaccion);
    }


}
