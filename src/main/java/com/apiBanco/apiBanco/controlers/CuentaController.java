package com.apiBanco.apiBanco.controlers;

import com.apiBanco.apiBanco.dtos.cuenta.CuentaRequestDTO;
import com.apiBanco.apiBanco.dtos.cuenta.CuentaResponseDTO;
import com.apiBanco.apiBanco.models.Cuenta;
import com.apiBanco.apiBanco.services.CuentaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService){
        this.cuentaService = cuentaService;
    }

    @GetMapping
    public ResponseEntity<Page<CuentaResponseDTO>> listarCuentas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String numeroDeCuenta
    ){

        Page<CuentaResponseDTO> listaCuentas = cuentaService.listarCuentas(page, size, numeroDeCuenta);
        return ResponseEntity.ok(listaCuentas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponseDTO> obtenerCuenta(@PathVariable Long id){
        CuentaResponseDTO dto = cuentaService.buscarCuentaPorId(id);
        return ResponseEntity.ok(dto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CuentaResponseDTO> actualizarCuenta(@PathVariable Long id, @Valid @RequestBody CuentaRequestDTO cuentaRequest){
        CuentaResponseDTO cuentaDto = cuentaService.actualizarCuenta(id, cuentaRequest);
        return ResponseEntity.ok(cuentaDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id){
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }
}
