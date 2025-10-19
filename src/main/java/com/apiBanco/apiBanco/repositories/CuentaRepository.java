package com.apiBanco.apiBanco.repositories;

import com.apiBanco.apiBanco.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    Optional<Cuenta> findByCliente_ClienteId(Long clienteId);

    boolean existsByNumeroDeCuenta(String numeroDeCuenta);

    Optional<Cuenta> findByAlias(String alias);
}
