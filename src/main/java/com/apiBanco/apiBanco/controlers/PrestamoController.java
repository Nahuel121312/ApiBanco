package com.apiBanco.apiBanco.controlers;

import com.apiBanco.apiBanco.models.Cliente;
import com.apiBanco.apiBanco.models.Prestamo;
import com.apiBanco.apiBanco.services.ClienteService;
import com.apiBanco.apiBanco.services.PrestamoService;
import lombok.Getter;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;
    private final ClienteService clienteService;

    public PrestamoController(PrestamoService prestamoService, ClienteService clienteService){
        this.prestamoService = prestamoService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Prestamo> listarPrestamos(){
        return prestamoService.listarPrestamos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtenerPrestamo(@PathVariable Long id){
        return prestamoService.buscarPrestamo(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Prestamo guardarPrestamo(@RequestBody Prestamo prestamo){

        Long clienteId = prestamo.getCliente().getClienteId();
        Cliente cliente = clienteService.obtenerCliente(clienteId)
                .orElseThrow(()-> new RuntimeException("Cliente no encontrado"));

        prestamo.setCliente(cliente);
        return prestamoService.guardarPrestamo(prestamo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prestamo> actualizarPrestamo(@PathVariable Long id, @RequestBody Prestamo prestamo){
        return prestamoService.buscarPrestamo(id)
                .map(p ->{
                    p.setMonto(prestamo.getMonto());
                    p.setTipoEstado(prestamo.getTipoEstado());
                    return ResponseEntity.ok(prestamoService.guardarPrestamo(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrestamo(@PathVariable Long id){
        if(prestamoService.buscarPrestamo(id).isPresent()){
            prestamoService.eliminarPrestamo(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
