package com.apiBanco.apiBanco.models;

import com.apiBanco.apiBanco.models.enums.TipoTransaccion;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter @Setter @NoArgsConstructor
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaccion;

    private LocalDateTime fecha;
    private boolean estado;
    private boolean enviada = false;
    private double monto;
    private String detalle;

    @Enumerated(EnumType.STRING)
    private TipoTransaccion tipoTransaccion;

    @ManyToOne
    @JoinColumn(name="cuenta_origen_id")
    @JsonBackReference("cuenta-transacciones-enviadas")
    private Cuenta cuentaOrigen;

    @ManyToOne
    @JoinColumn(name = "cuenta_destino_id")
    @JsonBackReference(value = "cuenta-transacciones-recibidas")
    private Cuenta cuentaDestino;

}
