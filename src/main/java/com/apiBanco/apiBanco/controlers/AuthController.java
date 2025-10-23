package com.apiBanco.apiBanco.controlers;

import com.apiBanco.apiBanco.dtos.cliente.ClienteRequestDTO;
import com.apiBanco.apiBanco.dtos.cliente.ClienteResponseDTO;
import com.apiBanco.apiBanco.dtos.login.LoginRequestDTO;
import com.apiBanco.apiBanco.dtos.login.LoginResponseDTO;
import com.apiBanco.apiBanco.models.Cliente;
import com.apiBanco.apiBanco.security.AuthService;
import com.apiBanco.apiBanco.services.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController (AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ClienteResponseDTO> registrar(@RequestBody ClienteRequestDTO request) {
        ClienteResponseDTO clienteResponse = authService.registrarCliente(request);
        return ResponseEntity.ok(clienteResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}
