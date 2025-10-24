package com.apiBanco.apiBanco.dtos.cliente;

import com.apiBanco.apiBanco.models.enums.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClienteRequestDTO {
    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacio")
    private String apellido;

    @Email(message = "El email debe ser valido")
    private String email;

    @NotBlank(message = "El dni no puede estar vacio")
    @Size(min = 7, max = 8, message = "El DNI debe tener entre 7 y 8 caracteres")
    private String dni;

    @NotBlank(message = "La direccion no puede estar vacia")
    private String direccion;

    @NotBlank(message = "El telefono no puede estar vacio")
    @Size(min = 10, message = "El telefono debe tener 10 caracteres")
    private String telefono;
    @NotBlank(message = "La contraseña no puede estar vacia")
    private String password;
    @NotBlank(message = "El username no puede estar vacio")
    private String username;

    private Rol rol;
}
