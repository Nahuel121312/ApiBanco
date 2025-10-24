package com.apiBanco.apiBanco.models;

import com.apiBanco.apiBanco.models.enums.TipoEstado;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter @Setter @NoArgsConstructor
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrestamo;
    private double monto;
    private double interes;
    private Long cuotas;
    private double valorCuota;
    private boolean estado;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDateTime fechaSolicitud;

    @Enumerated(EnumType.STRING)
    private TipoEstado tipoEstado;

    @ManyToOne
    @JoinColumn(name = "id_cuenta")
    private Cuenta cuenta;

}
