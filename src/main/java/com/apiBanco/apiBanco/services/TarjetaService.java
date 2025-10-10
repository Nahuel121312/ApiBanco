package com.apiBanco.apiBanco.services;

import com.apiBanco.apiBanco.models.Tarjeta;
import com.apiBanco.apiBanco.repositories.TarjetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TarjetaService {

    private final TarjetaRepository tarjetaRepository;

    public TarjetaService(TarjetaRepository tarjetaRepository){
        this.tarjetaRepository = tarjetaRepository;
    }

    //Listar tarjetas
    public List<Tarjeta> listarTarjetas(){
        return tarjetaRepository.findAll();
    }

    //Buscar por id
    public Optional<Tarjeta> buscarPorId(Long id){
        return tarjetaRepository.findById(id);
    }

    //Guardar Tarjeta
    public Tarjeta guardarTarjeta (Tarjeta tarjeta){
        return tarjetaRepository.save(tarjeta);
    }

    //Eliminar Tarjeta
    public void eliminarTarjeta(Long id){
        tarjetaRepository.deleteById(id);
    }





}
