package com.apiBanco.apiBanco.controlers;

import com.apiBanco.apiBanco.dtos.transaccion.TransaccionRequestDTO;
import com.apiBanco.apiBanco.dtos.transaccion.TransaccionResponseDTO;
import com.apiBanco.apiBanco.models.Cuenta;
import com.apiBanco.apiBanco.models.Transaccion;
import com.apiBanco.apiBanco.models.enums.TipoEstado;
import com.apiBanco.apiBanco.models.enums.TipoTransaccion;
import com.apiBanco.apiBanco.services.CuentaService;
import com.apiBanco.apiBanco.services.TransaccionService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/transacciones")
public class TransaccionController {

    public final TransaccionService transaccionService;
    public final CuentaService cuentaService;

    public TransaccionController (TransaccionService transaccionService, CuentaService cuentaService){
        this.transaccionService = transaccionService;
        this.cuentaService = cuentaService;
    }

    @GetMapping
    public ResponseEntity<Page<TransaccionResponseDTO>> listarTransacciones(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false)TipoTransaccion tipoTransaccion){

        Page<TransaccionResponseDTO> listarTransacciones = transaccionService.listarTransacciones(page, size, tipoTransaccion);

        return ResponseEntity.ok(listarTransacciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionResponseDTO> buscarTransaccion(@PathVariable Long id){
        TransaccionResponseDTO transaccionResponse = transaccionService.obtenerTransaccion(id);
        return ResponseEntity.ok(transaccionResponse);
    }

   @PostMapping
   public TransaccionResponseDTO crearTransaccion(@Valid @RequestBody TransaccionRequestDTO transaccionRequest){
        return transaccionService.crearTransaccion(transaccionRequest);
   }

    @PreAuthorize("hasRole('ADMIN')")
   @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTransaccion(@PathVariable Long id){
        transaccionService.eliminarTransaccion(id);
        return ResponseEntity.noContent().build();
   }


}
