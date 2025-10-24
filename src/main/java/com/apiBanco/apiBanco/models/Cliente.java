package com.apiBanco.apiBanco.models;

import com.apiBanco.apiBanco.models.enums.Rol;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@Entity
@Getter @Setter @NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "clienteId")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;

    private String nombre;
    private String apellido;

    @Column(unique = true)
    private String dni;

    @Column(unique = true)
    private String email;

    private String telefono;
    private String direccion;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String password;

    private boolean estado;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy/MM/dd")
    private LocalDateTime fechaRegistro;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cuenta cuenta;



}
