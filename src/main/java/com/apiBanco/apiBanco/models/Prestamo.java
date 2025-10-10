package com.apiBanco.apiBanco.models;

import com.apiBanco.apiBanco.models.enums.TipoEstado;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDateTime fechaSolicitud;

    @Enumerated(EnumType.STRING)
    private TipoEstado tipoEstado;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonBackReference(value = "cliente-prestamos")
    private Cliente cliente;

    public Prestamo(double monto, double interes, Long cuotas, TipoEstado tipoEstado, Cliente cliente){
        this.monto = monto;
        this.interes = interes;
        this.cuotas = cuotas;
        this.tipoEstado = tipoEstado;
        this.cliente = cliente;
    }
    @PrePersist
    public void prePersist(){
        this.fechaSolicitud = LocalDateTime.now();
    }
}
