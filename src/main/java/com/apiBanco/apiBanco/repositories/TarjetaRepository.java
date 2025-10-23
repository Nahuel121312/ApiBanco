package com.apiBanco.apiBanco.repositories;

import com.apiBanco.apiBanco.models.Prestamo;
import com.apiBanco.apiBanco.models.Tarjeta;
import com.apiBanco.apiBanco.models.enums.TipoTarjeta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {

    Page<Tarjeta> findByEstadoTrue(Pageable pageable);

    Page<Tarjeta> findByTipoTarjetaAndEstadoTrue(TipoTarjeta tipoTarjeta, Pageable pageable);
}
