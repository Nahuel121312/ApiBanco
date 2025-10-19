package com.apiBanco.apiBanco.services;

import com.apiBanco.apiBanco.dtos.prestamo.PrestamoRequestDTO;
import com.apiBanco.apiBanco.dtos.prestamo.PrestamoResponseDTO;
import com.apiBanco.apiBanco.mapper.PrestamoMapper;
import com.apiBanco.apiBanco.models.Cliente;
import com.apiBanco.apiBanco.models.Cuenta;
import com.apiBanco.apiBanco.models.Prestamo;
import com.apiBanco.apiBanco.models.enums.TipoEstado;
import com.apiBanco.apiBanco.repositories.CuentaRepository;
import com.apiBanco.apiBanco.repositories.PrestamoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final CuentaRepository cuentaRepository;
    private final PrestamoMapper prestamoMapper;
    public PrestamoService(PrestamoRepository prestamoRepository, CuentaRepository cuentaRepository, PrestamoMapper prestamoMapper){
        this.prestamoRepository = prestamoRepository;
        this.cuentaRepository = cuentaRepository;
        this.prestamoMapper = prestamoMapper;
    }

    //Listar prestamos
    public List<PrestamoResponseDTO> listarPrestamos(){
        return prestamoRepository.findByEstadoTrue()
                .stream().map(prestamoMapper::toResponse)
                .toList();
    }

    //Buscar prestamo
    public Optional<Prestamo> buscarPrestamo(Long id){
        return prestamoRepository.findById(id);
    }

    //Guardar Prestamo
    public PrestamoResponseDTO crearPrestamo(PrestamoRequestDTO prestamoRequest){


        double interes = 0.5;
        Long n = prestamoRequest.getCuotas();
        double valorCuota = (prestamoRequest.getMonto() * interes * Math.pow(1 + interes, n)) / (Math.pow(1 + interes, n) -1);

        Prestamo prestamo = prestamoMapper.toEntity(prestamoRequest);

        prestamo.setMonto(prestamoRequest.getMonto());
        prestamo.setCuotas(n);
        prestamo.setTipoEstado(TipoEstado.APROBADO);
        prestamo.setValorCuota(valorCuota);
        prestamo.setFechaSolicitud(LocalDateTime.now());


        if(prestamo.getTipoEstado() == TipoEstado.APROBADO){
            Cuenta cuenta = prestamo.getCuenta();
            cuenta.setSaldo(cuenta.getSaldo() + prestamo.getMonto());
            cuentaRepository.save(cuenta);
        }

        prestamoRepository.save(prestamo);

        return prestamoMapper.toResponse(prestamo);
    }

    //Actualizar Prestamo
    public PrestamoResponseDTO actualizarPrestamo (Long id, PrestamoRequestDTO dto){
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Prestamo no encontrado"));

        prestamo.setMonto(dto.getMonto());
        prestamo.setCuotas(dto.getCuotas());

        Cuenta cuenta = new Cuenta();
        cuenta.setIdCuenta(dto.getIdCuenta());
        prestamo.setCuenta(cuenta);

        return prestamoMapper.toResponse(prestamo);
    }

    //Eliminar Prestamo
    public void eliminarPrestamo(Long id){
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Prestamo no encontrado"));
        prestamo.setEstado(false);
    }


}
