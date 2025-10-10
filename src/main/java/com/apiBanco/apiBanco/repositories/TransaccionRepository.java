package com.apiBanco.apiBanco.repositories;

import com.apiBanco.apiBanco.models.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {


}
