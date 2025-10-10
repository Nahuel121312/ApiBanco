package com.apiBanco.apiBanco.repositories;

import com.apiBanco.apiBanco.models.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {


}
