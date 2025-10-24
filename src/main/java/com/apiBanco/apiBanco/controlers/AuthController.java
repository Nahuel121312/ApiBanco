package com.apiBanco.apiBanco.controlers;

import com.apiBanco.apiBanco.dtos.cliente.ClienteRequestDTO;
import com.apiBanco.apiBanco.dtos.cliente.ClienteResponseDTO;
import com.apiBanco.apiBanco.dtos.login.LoginRequestDTO;
import com.apiBanco.apiBanco.dtos.login.LoginResponseDTO;
import com.apiBanco.apiBanco.security.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
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
