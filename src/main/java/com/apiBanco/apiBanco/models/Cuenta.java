package com.apiBanco.apiBanco.models;

import com.apiBanco.apiBanco.models.enums.TipoCuenta;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuenta;

    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;

    private String numeroDeCuenta;
    private double saldo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDateTime fechaApertura;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cliente_id")
    @JsonBackReference(value = "cliente-cuentas")
    private Cliente cliente;

    @OneToMany(mappedBy = "cuentaOrigen", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "cuenta-transacciones-enviadas")
    private List<Transaccion> transaccionesEnviadas = new ArrayList<>();

    @OneToMany(mappedBy = "cuentaDestino", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "cuenta-transacciones-recibidas")
    private List<Transaccion> transaccionesRecibidas = new ArrayList<>();

    public Cuenta (TipoCuenta tipoCuenta, String numeroDeCuenta, double saldo, Cliente cliente){
        this.tipoCuenta = tipoCuenta;
        this.numeroDeCuenta = numeroDeCuenta;
        this.saldo = saldo;
        this.cliente = cliente;
    }

    @PrePersist
    public void prePersist(){
        this.fechaApertura = LocalDateTime.now();
    }
}
