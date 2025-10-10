package com.apiBanco.apiBanco.controlers;

import com.apiBanco.apiBanco.models.Cuenta;
import com.apiBanco.apiBanco.models.Transaccion;
import com.apiBanco.apiBanco.services.CuentaService;
import com.apiBanco.apiBanco.services.TransaccionService;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Transaccion> listarTransacciones(){
        return transaccionService.listarTransacciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaccion> buscarTransaccion(@PathVariable Long id){
        return transaccionService.obtenerTransaccion(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

   @PostMapping
   public Transaccion guardarTransaccion(@RequestBody Transaccion transaccion){

        Cuenta cuentaOrigen = cuentaService.buscarCuentaPorId(transaccion.getCuentaOrigen().getIdCuenta())
                .orElseThrow(()-> new RuntimeException("Cuenta origen no encontrada"));

        Cuenta cuentaDestino = cuentaService.buscarCuentaPorId(transaccion.getCuentaDestino().getIdCuenta())
                .orElseThrow(()-> new RuntimeException("Cuenta destino no encontrada"));
        transaccion.setCuentaOrigen(cuentaOrigen);
        transaccion.setCuentaDestino(cuentaDestino);
        return transaccionService.guardarTransaccion(transaccion);
   }

   @PutMapping("{id}")

   @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTransaccion(@PathVariable Long id){
            if(transaccionService.obtenerTransaccion(id).isPresent()){
                transaccionService.eliminarTransaccion(id);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
   }


}
