package com.iot.backend.controller;

import com.iot.backend.model.Leitura;
import com.iot.backend.repository.LeituraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teste")
@CrossOrigin(origins = "*")
public class TesteController {
    
    @Autowired
    private LeituraRepository leituraRepository;
    
    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "online");
        status.put("timestamp", LocalDateTime.now().toString());
        status.put("totalLeituras", leituraRepository.count());
        return status;
    }
    
    @GetMapping("/leituras")
    public List<Leitura> getTodasLeituras() {
        return leituraRepository.findAll();
    }
    
    @PostMapping("/simular")
    public String simularLeitura() {
        // Criar leitura de teste
        Leitura teste = new Leitura("TESTE_001", "agua", 15.5, "L/min", false);
        leituraRepository.save(teste);
        
        Leitura teste2 = new Leitura("TESTE_002", "energia", 750.0, "W", false);
        leituraRepository.save(teste2);
        
        Leitura teste3 = new Leitura("TESTE_003", "gas", 45.0, "ppm", false);
        leituraRepository.save(teste3);
        
        return "3 leituras de teste criadas! Acesse /api/dashboard/dados para ver";
    }
}