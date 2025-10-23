package com.apiBanco.apiBanco.repositories;

import com.apiBanco.apiBanco.models.Cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Page<Cliente> findByEstadoTrue(Pageable pageable);

    Page<Cliente> findByNombreContainingIgnoreCaseAndEstadoTrue(String nombre, Pageable pageable);

    Optional<Cliente> findByUsername(String username);
}
