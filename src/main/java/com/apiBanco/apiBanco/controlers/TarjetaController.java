package com.apiBanco.apiBanco.controlers;

import com.apiBanco.apiBanco.models.Cliente;
import com.apiBanco.apiBanco.models.Prestamo;
import com.apiBanco.apiBanco.models.Tarjeta;
import com.apiBanco.apiBanco.services.ClienteService;
import com.apiBanco.apiBanco.services.PrestamoService;
import com.apiBanco.apiBanco.services.TarjetaService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarjetas")
public class TarjetaController {

    private final TarjetaService tarjetaService;
    private final ClienteService clienteService;

    public TarjetaController(TarjetaService tarjetaService, ClienteService clienteService){
        this.tarjetaService = tarjetaService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Tarjeta> listarTarjetas(){
        return tarjetaService.listarTarjetas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarjeta> obtenerTarjeta(@PathVariable Long id){
        return tarjetaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tarjeta guardarTarjeta(@RequestBody Tarjeta tarjeta){

        Long clienteId = tarjeta.getCliente().getClienteId();
        Cliente cliente = clienteService.obtenerCliente(clienteId)
                .orElseThrow(()-> new RuntimeException("Cliente no encontrado"));
        tarjeta.setCliente(cliente);
        return tarjetaService.guardarTarjeta(tarjeta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarjeta> actualizarTarjeta(@PathVariable Long id, @RequestBody Tarjeta tarjeta){
        return tarjetaService.buscarPorId(id)
                .map(t ->{
                    t.setLimiteCredito(tarjeta.getLimiteCredito());
                    return ResponseEntity.ok(tarjetaService.guardarTarjeta(t));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarjeta(@PathVariable Long id){
        if(tarjetaService.buscarPorId(id).isPresent()){
            tarjetaService.eliminarTarjeta(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
