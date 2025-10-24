package com.apiBanco.apiBanco.repositories;

import com.apiBanco.apiBanco.models.Transaccion;
import com.apiBanco.apiBanco.models.enums.TipoTransaccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    Page<Transaccion> findByEstadoTrue(Pageable pageable);

    Page<Transaccion> findByTipoTransaccionAndEstadoTrue(TipoTransaccion tipoTransaccion, Pageable pageable);
}
