package com.apiBanco.apiBanco.controlers;

import com.apiBanco.apiBanco.dtos.cuenta.CuentaRequestDTO;
import com.apiBanco.apiBanco.dtos.cuenta.CuentaResponseDTO;
import com.apiBanco.apiBanco.services.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<CuentaResponseDTO>> listarCuentas(){
        List<CuentaResponseDTO> listaCuentasDTO = cuentaService.listarCuentas();
        return ResponseEntity.ok(listaCuentasDTO);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id){
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }
}
