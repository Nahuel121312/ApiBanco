package com.apiBanco.apiBanco.controlers;

import com.apiBanco.apiBanco.dtos.tarjeta.TarjetaRequestDTO;
import com.apiBanco.apiBanco.dtos.tarjeta.TarjetaResponseDTO;
import com.apiBanco.apiBanco.models.Cliente;
import com.apiBanco.apiBanco.models.Prestamo;
import com.apiBanco.apiBanco.models.Tarjeta;
import com.apiBanco.apiBanco.services.ClienteService;
import com.apiBanco.apiBanco.services.PrestamoService;
import com.apiBanco.apiBanco.services.TarjetaService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/tarjetas")
public class TarjetaController {

    private final TarjetaService tarjetaService;

    public TarjetaController(TarjetaService tarjetaService){
        this.tarjetaService = tarjetaService;
    }

    @GetMapping
    public ResponseEntity<List<TarjetaResponseDTO>> listarTarjetas(){
        List<TarjetaResponseDTO> listaTarjetas = tarjetaService.listarTarjetas();
        return ResponseEntity.ok(listaTarjetas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarjetaResponseDTO> obtenerTarjeta(@PathVariable Long id){
        TarjetaResponseDTO tarjeraResponse = tarjetaService.buscarPorId(id);
        return ResponseEntity.ok(tarjeraResponse);
    }

    @PostMapping
    public ResponseEntity<TarjetaResponseDTO> crearTarjeta(@Valid @RequestBody TarjetaRequestDTO tarjetaRequest){
        TarjetaResponseDTO tarjetaResponse = tarjetaService.crearTarjeta(tarjetaRequest);
        return ResponseEntity.ok(tarjetaResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarjetaResponseDTO> actualizarTarjeta(@PathVariable Long id, @Valid @RequestBody TarjetaRequestDTO tarjetaRequest){
        TarjetaResponseDTO tarjetaResponse = tarjetaService.actualizarTarjeta(id,tarjetaRequest);
        return ResponseEntity.ok(tarjetaResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarjeta(@PathVariable Long id){
        tarjetaService.eliminarTarjeta(id);
        return ResponseEntity.noContent().build();
    }


}
