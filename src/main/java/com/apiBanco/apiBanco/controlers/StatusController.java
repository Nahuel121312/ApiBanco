package com.apiBanco.apiBanco.controlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/status")
@CrossOrigin(origins = "http://localhost:5173")
public class StatusController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> getStatus(){
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("timestap", LocalDateTime.now());
        response.put("message", "API Banco funcionando correctamente");
        return ResponseEntity.ok(response);
    }
}
