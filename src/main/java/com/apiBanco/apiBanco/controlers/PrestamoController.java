package com.apiBanco.apiBanco.controlers;

import com.apiBanco.apiBanco.dtos.prestamo.PrestamoRequestDTO;
import com.apiBanco.apiBanco.dtos.prestamo.PrestamoResponseDTO;
import com.apiBanco.apiBanco.models.enums.TipoEstado;
import com.apiBanco.apiBanco.services.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService){
        this.prestamoService = prestamoService;
    }

    @GetMapping
    public ResponseEntity<Page<PrestamoResponseDTO>> listarPrestamos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) TipoEstado tipoEstado){

        Page<PrestamoResponseDTO> listaPrestamos = prestamoService.listarPrestamos(page, size, tipoEstado);

        return ResponseEntity.ok(listaPrestamos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestamoResponseDTO> obtenerPrestamo(@PathVariable Long id){
        PrestamoResponseDTO prestamoResponse = prestamoService.buscarPrestamo(id);
        return ResponseEntity.ok(prestamoResponse);
    }

    @PostMapping
    public ResponseEntity<PrestamoResponseDTO> crearPrestamo(@Valid @RequestBody PrestamoRequestDTO prestamoRequest){
        PrestamoResponseDTO prestamoResponse = prestamoService.crearPrestamo(prestamoRequest);

        return ResponseEntity.ok(prestamoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrestamoResponseDTO> actualizarPrestamo(@PathVariable Long id,@Valid @RequestBody PrestamoRequestDTO prestamoRequest ){
        PrestamoResponseDTO prestamoResponse = prestamoService.actualizarPrestamo(id, prestamoRequest);

        return ResponseEntity.ok(prestamoResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrestamo(@PathVariable Long id){
        prestamoService.eliminarPrestamo(id);
        return ResponseEntity.noContent().build();
    }

}
