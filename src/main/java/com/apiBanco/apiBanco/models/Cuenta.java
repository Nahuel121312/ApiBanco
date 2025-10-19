package com.apiBanco.apiBanco.models;

import com.apiBanco.apiBanco.models.enums.TipoCuenta;
import com.fasterxml.jackson.annotation.*;
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
    private String alias;
    private boolean estado;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDateTime fechaApertura;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prestamo> prestamos = new ArrayList<>();

    @OneToMany(mappedBy = "cuentaDestino", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "cuenta-transacciones")
    private List<Transaccion> transacciones = new ArrayList<>();

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "cuenta-tarjetas")
    private List<Tarjeta> tarjetas = new ArrayList<>();


}
