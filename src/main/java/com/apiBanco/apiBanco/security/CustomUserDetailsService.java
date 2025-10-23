package com.apiBanco.apiBanco.security;

import com.apiBanco.apiBanco.models.Cliente;
import com.apiBanco.apiBanco.repositories.ClienteRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ClienteRepository clienteRepository;

    public CustomUserDetailsService (ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("Cliente no encontrado"));

        return new User(
                cliente.getUsername(),
                cliente.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + cliente.getRol()))
        );
    }
}
