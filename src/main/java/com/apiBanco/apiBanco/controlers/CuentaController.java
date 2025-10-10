package com.apiBanco.apiBanco.controlers;

import com.apiBanco.apiBanco.models.Cliente;
import com.apiBanco.apiBanco.models.Cuenta;
import com.apiBanco.apiBanco.services.ClienteService;
import com.apiBanco.apiBanco.services.CuentaService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;
    private final ClienteService clienteService;

    public CuentaController(CuentaService cuentaService, ClienteService clienteService){
        this.cuentaService = cuentaService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cuenta> listarCuentas(){
        return cuentaService.listarCuentas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> obtenerCuenta(@PathVariable Long id){
        return cuentaService.buscarCuentaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cuenta crearCuenta(@RequestBody Cuenta cuenta){
        System.out.println("Cliente recibido: " + cuenta.getCliente());
        System.out.println("ID recibido: " + cuenta.getCliente().getClienteId());

        if(cuenta.getCliente() == null || cuenta.getCliente().getClienteId() == null){
            throw new IllegalArgumentException("Debe especificarse el id del cliente");
        }

        Long clienteId = cuenta.getCliente().getClienteId();
        Cliente cliente = clienteService.obtenerCliente(clienteId)
                .orElseThrow(()-> new RuntimeException("Cliente no encontrado"));
        cuenta.setCliente(cliente);
        return cuentaService.guardarCuenta(cuenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta){
        return cuentaService.buscarCuentaPorId(id)
                .map(c ->{
                    c.setSaldo(cuenta.getSaldo());
                    c.setTipoCuenta(cuenta.getTipoCuenta());
                    return ResponseEntity.ok(cuentaService.guardarCuenta(c));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id){
        if(cuentaService.buscarCuentaPorId(id).isPresent()){
            cuentaService.eliminarCuenta(id);
            return ResponseEntity.noContent().build();
        }
            return ResponseEntity.notFound().build();
    }
}
