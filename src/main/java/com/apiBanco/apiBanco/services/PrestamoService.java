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
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<PrestamoResponseDTO> listarPrestamos(int page, int size, TipoEstado tipoEstado){
        Pageable pageable = PageRequest.of(page, size);
        Page<Prestamo> listaPrestamos;
        if (tipoEstado != null) {
            listaPrestamos = prestamoRepository.findByTipoEstadoAndEstadoTrue(tipoEstado, pageable);
        } else {
            listaPrestamos = prestamoRepository.findByEstadoTrue(pageable);
        }

        return listaPrestamos.map(prestamoMapper::toResponse);
    }

    //Buscar prestamo
    public PrestamoResponseDTO buscarPrestamo(Long id){
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Prestamo con ID: "+ id + " no encontrado"));
        return prestamoMapper.toResponse(prestamo);
    }

    //Guardar Prestamo
    public PrestamoResponseDTO crearPrestamo(PrestamoRequestDTO prestamoRequest){
        Prestamo prestamo = prestamoMapper.toEntity(prestamoRequest);
        Long cuentaId = prestamo.getCuenta().getIdCuenta();
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(()-> new EntityNotFoundException("Cuenta con ID: "+ cuentaId + " no encontrado"));
        prestamo.setCuenta(cuenta);

        double monto = prestamoRequest.getMonto();
        long cuotas = prestamoRequest.getCuotas();
        double interesAnual = 60;
        double interesMensual;
        double valorCuota;

        if(cuotas == 1){
            valorCuota = monto;
            interesMensual = 0;
        }else {
            interesMensual = (interesAnual / 100 )/ 12;
            valorCuota = (monto * interesMensual * Math.pow(1 + interesMensual, cuotas)) / (Math.pow(1 + interesMensual, cuotas) -1);
        }
        valorCuota = Math.round(valorCuota * 100.0) / 100.0;

        prestamo.setMonto(monto);
        prestamo.setCuotas(cuotas);
        prestamo.setTipoEstado(TipoEstado.APROBADO);
        prestamo.setValorCuota(valorCuota);
        prestamo.setFechaSolicitud(LocalDateTime.now());
        prestamo.setInteres(interesMensual);
        prestamo.setEstado(true);

        if(prestamo.getTipoEstado() == TipoEstado.APROBADO){
            cuenta.setSaldo(cuenta.getSaldo() + prestamo.getMonto());
            cuentaRepository.save(cuenta);
        }

        prestamoRepository.save(prestamo);
        return prestamoMapper.toResponse(prestamo);
    }

    //Actualizar Prestamo
    public PrestamoResponseDTO actualizarPrestamo (Long id, PrestamoRequestDTO dto){
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Prestamo con ID: "+ id + " no encontrado"));

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
                .orElseThrow(()-> new EntityNotFoundException("Prestamo con ID: "+ id + " no encontrado"));
        prestamo.setEstado(false);
    }


}
