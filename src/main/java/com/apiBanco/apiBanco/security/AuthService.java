package com.apiBanco.apiBanco.security;

import com.apiBanco.apiBanco.dtos.cliente.ClienteRequestDTO;
import com.apiBanco.apiBanco.dtos.cliente.ClienteResponseDTO;
import com.apiBanco.apiBanco.dtos.login.LoginRequestDTO;
import com.apiBanco.apiBanco.dtos.login.LoginResponseDTO;
import com.apiBanco.apiBanco.models.Cliente;
import com.apiBanco.apiBanco.repositories.ClienteRepository;
import com.apiBanco.apiBanco.services.ClienteManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ClienteManager clienteManager;
    private final ClienteRepository clienteRepository;

    public AuthService (AuthenticationManager authenticationManager,
                        JwtService jwtService,
                        ClienteManager clienteManager,
                        ClienteRepository clienteRepository){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.clienteManager = clienteManager;
        this.clienteRepository = clienteRepository;
    }

    //Registrar nuevo cliente
    public ClienteResponseDTO registrarCliente(ClienteRequestDTO clienteRequest) {
        return clienteManager.crearClienteConCuentaYTarjera(clienteRequest);
    }

    public LoginResponseDTO login (LoginRequestDTO loginRequest) {

        System.out.println("Username:"+loginRequest.getUsername());

        //Verifica usuario y contraseña
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        //Obtiene los datos del usuario autenticado
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        Cliente cliente = clienteRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(()-> new RuntimeException("Cliente no encontrado"));

        String token = jwtService.generarToken(userDetails.getUsername(), cliente.getRol().toString());

        return new LoginResponseDTO(token);
    }
}
