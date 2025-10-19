package com.apiBanco.apiBanco.controlers;

import com.apiBanco.apiBanco.dtos.prestamo.PrestamoRequestDTO;
import com.apiBanco.apiBanco.dtos.prestamo.PrestamoResponseDTO;
import com.apiBanco.apiBanco.models.Cliente;
import com.apiBanco.apiBanco.models.Prestamo;
import com.apiBanco.apiBanco.services.ClienteService;
import com.apiBanco.apiBanco.services.PrestamoService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService){
        this.prestamoService = prestamoService;
    }

    @GetMapping
    public List<PrestamoResponseDTO> listarPrestamos(){
        return prestamoService.listarPrestamos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtenerPrestamo(@PathVariable Long id){
        return prestamoService.buscarPrestamo(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrestamo(@PathVariable Long id){
        prestamoService.eliminarPrestamo(id);
        return ResponseEntity.noContent().build();
    }

}
