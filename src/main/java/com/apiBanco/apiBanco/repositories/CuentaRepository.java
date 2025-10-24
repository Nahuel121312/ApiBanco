package com.apiBanco.apiBanco.repositories;

import com.apiBanco.apiBanco.models.Cliente;
import com.apiBanco.apiBanco.models.Cuenta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    boolean existsByNumeroDeCuenta(String numeroDeCuenta);

    boolean existsByCliente_ClienteId(Long clienteId);

    Page<Cuenta> findByEstadoTrue(Pageable pageable);

    Page<Cuenta> findByNumeroDeCuentaContainingAndEstadoTrue(String numeroDeCuenta, Pageable pageable);

}
