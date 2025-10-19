package com.apiBanco.apiBanco.repositories;

import com.apiBanco.apiBanco.models.Prestamo;
import com.apiBanco.apiBanco.models.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {

    List<Tarjeta> existsByEstadoTrue();
}
