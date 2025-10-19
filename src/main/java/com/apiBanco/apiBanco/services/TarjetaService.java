package com.apiBanco.apiBanco.services;

import com.apiBanco.apiBanco.dtos.tarjeta.TarjetaRequestDTO;
import com.apiBanco.apiBanco.dtos.tarjeta.TarjetaResponseDTO;
import com.apiBanco.apiBanco.mapper.PrestamoMapper;
import com.apiBanco.apiBanco.mapper.TarjetaMapper;
import com.apiBanco.apiBanco.models.Cuenta;
import com.apiBanco.apiBanco.models.Tarjeta;
import com.apiBanco.apiBanco.models.enums.TipoTarjeta;
import com.apiBanco.apiBanco.repositories.CuentaRepository;
import com.apiBanco.apiBanco.repositories.TarjetaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class TarjetaService {

    private final TarjetaRepository tarjetaRepository;
    private final CuentaRepository cuentaRepository;
    private final TarjetaMapper tarjetaMapper;
    private final Random random = new Random();

    public TarjetaService(TarjetaRepository tarjetaRepository, CuentaRepository cuentaRepository, TarjetaMapper tarjetaMapper){
        this.tarjetaRepository = tarjetaRepository;
        this.cuentaRepository = cuentaRepository;
        this.tarjetaMapper = tarjetaMapper;
    }

    //Listar tarjetas
    public List<TarjetaResponseDTO> listarTarjetas(){
        return tarjetaRepository.existsByEstadoTrue()
                .stream().map(tarjetaMapper::toResponse)
                .toList();
    }

    //Buscar por id
    public TarjetaResponseDTO buscarPorId(Long id){
        Tarjeta tarjeta = tarjetaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Tarjeta no encontrada"));
        return tarjetaMapper.toResponse(tarjeta);
    }

    //Crear tarjeta
    public TarjetaResponseDTO crearTarjeta (TarjetaRequestDTO tarjetaRequest){
        Tarjeta tarjeta = new Tarjeta();

        tarjeta.setNumeroDeTarjeta(generarNumeroTarjeta());
        tarjeta.setTipoTarjeta(tarjetaRequest.getTipoTarjeta());
        tarjeta.setLimiteCredito(1000000);
        tarjeta.setFechaVencimiento(LocalDate.now());
        tarjeta.setEstado(true);
        Cuenta cuenta = new Cuenta();
        cuenta.setIdCuenta(cuenta.getIdCuenta());
        tarjeta.setCuenta(cuenta);

        tarjetaRepository.save(tarjeta);
        return tarjetaMapper.toResponse(tarjeta);
    }

    //Crear TarjetaconCuenta
    public void crearTarjetaConCuenta (Long cuentaId){
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(()-> new RuntimeException("Cuenta no encontrada"));

        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setCuenta(cuenta);
        tarjeta.setTipoTarjeta(TipoTarjeta.DEBITO);
        tarjeta.setNumeroDeTarjeta(generarNumeroTarjeta());
        tarjeta.setLimiteCredito(100000);

        tarjetaRepository.save(tarjeta);
    }

    //Eliminar Tarjeta
    public void eliminarTarjeta(Long id){
        Tarjeta tarjeta = tarjetaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Tarjeta no encontrada"));
        tarjeta.setEstado(false);
    }

    //ActualizarTarjeta
    public TarjetaResponseDTO actualizarTarjeta(Long id, TarjetaRequestDTO tarjetaRequest){
        Tarjeta nueva = tarjetaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Tarjeta no encontrada"));


        Cuenta cuenta = new Cuenta();
        cuenta.setIdCuenta(tarjetaRequest.getCuentaId());
        nueva.setCuenta(cuenta);

        nueva.setLimiteCredito(1000000);
        tarjetaRepository.save(nueva);

        return tarjetaMapper.toResponse(nueva);
    }


    //Generar numeros de tarjetas
    public String generarNumeroTarjeta(){
        String bin = "454321";
        StringBuilder numero = new StringBuilder(bin);

        for(int i = 0; i < 9; i++){
            numero.append(random.nextInt(10));
        }
        return numero.toString();
    }



}
