package com.apiBanco.apiBanco.controlers;

import com.apiBanco.apiBanco.dtos.cliente.ClienteRequestDTO;
import com.apiBanco.apiBanco.dtos.cliente.ClienteResponseDTO;
import com.apiBanco.apiBanco.services.ClienteManager;
import com.apiBanco.apiBanco.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteManager clienteManager;

    public ClienteController (ClienteService clienteService, ClienteManager clienteManager){
        this.clienteService = clienteService;
        this.clienteManager = clienteManager;
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResponseDTO>> listarClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false)String nombre){

        Page<ClienteResponseDTO> clientesPage = clienteService.listarClientes(page, size,nombre);
        return ResponseEntity.ok(clientesPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerCliente(@PathVariable Long id){
        ClienteResponseDTO clienteDTO = clienteService.obtenerCliente(id);
        return ResponseEntity.ok(clienteDTO);
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crearCliente(@Valid @RequestBody ClienteRequestDTO dto){
        ClienteResponseDTO nuevo = clienteManager.crearClienteConCuentaYTarjera(dto);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO clienteRequestDTO){
        ClienteResponseDTO clienteResponseDTO = clienteService.actualizarCliente(id, clienteRequestDTO);
        return ResponseEntity.ok(clienteResponseDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }


}
