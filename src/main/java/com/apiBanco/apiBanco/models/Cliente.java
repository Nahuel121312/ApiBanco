package com.apiBanco.apiBanco.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;

    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String telefono;
    private String direccion;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy/MM/dd")
    private LocalDateTime fechaRegistro;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "cliente-cuentas")
    private List<Cuenta> cuentas = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "cliente-tarjetas")
    private List<Tarjeta> tarjetas = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "cliente-prestamos")
    private List<Prestamo> prestamos = new ArrayList<>();

    public Cliente (String nombre, String apellido, String email, String telefono, String direccion, String dni){
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechaRegistro = LocalDateTime.now();
        this.dni = dni;
    }
    @PrePersist
    public void prePersist(){
        this.fechaRegistro = LocalDateTime.now();
    }


}
