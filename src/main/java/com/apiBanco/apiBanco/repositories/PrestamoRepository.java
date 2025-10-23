package com.apiBanco.apiBanco.repositories;

import com.apiBanco.apiBanco.models.Prestamo;
import com.apiBanco.apiBanco.models.enums.TipoEstado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    Page<Prestamo> findByEstadoTrue(Pageable pageable);

    Page<Prestamo> findByTipoEstadoAndEstadoTrue(TipoEstado tipoEstado, Pageable pageable);
}
