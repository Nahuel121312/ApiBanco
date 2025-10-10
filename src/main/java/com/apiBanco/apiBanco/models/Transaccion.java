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

    @Enumerated(EnumType.STRING)
    private TipoTransaccion tipoTransaccion;

    private double monto;
    private String detalle;
    @ManyToOne
    @JoinColumn(name="cuenta_origen_id")
    @JsonBackReference("cuenta-transacciones-enviadas")
    private Cuenta cuentaOrigen;

    @ManyToOne
    @JoinColumn(name = "cuenta_destino_id")
    @JsonBackReference(value = "cuenta-transacciones-recibidas")
    private Cuenta cuentaDestino;


    public Transaccion(TipoTransaccion tipoTransaccion, double monto, String detalle, Cuenta cuentaOrigen, Cuenta cuentaDestino){
        this.tipoTransaccion = tipoTransaccion;
        this.monto = monto;
        this.detalle = detalle;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
    }
}
