package com.apiBanco.apiBanco.models;

import com.apiBanco.apiBanco.models.enums.TipoTarjeta;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Getter @Setter @NoArgsConstructor
public class Tarjeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarjeta;
    private String numeroDeTarjeta;
    private boolean estado;
    private double limiteCredito;

    @Enumerated(EnumType.STRING)
    private TipoTarjeta tipoTarjeta;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM")
    private LocalDate fechaVencimiento;

    @ManyToOne
    @JoinColumn(name = "cuenta_id")
    @JsonBackReference(value = "cuenta-tarjetas")
    private Cuenta cuenta;

}
