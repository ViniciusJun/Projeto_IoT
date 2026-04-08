package com.iot.backend.controller;

import com.iot.backend.model.Leitura;
import com.iot.backend.repository.LeituraRepository;
import com.iot.backend.service.AlertaService;
import com.iot.backend.service.DicasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {
    
    @Autowired
    private LeituraRepository leituraRepository;
    
    @Autowired
    private AlertaService alertaService;
    
    @Autowired
    private DicasService dicasService;
    
    @GetMapping("/dados")
    public Map<String, Object> getDadosDashboard() {
        Map<String, Object> dados = new HashMap<>();
        
        // Últimas leituras
        dados.put("ultimaAgua", leituraRepository.findTopByTipoOrderByTimestampDesc("agua"));
        dados.put("ultimaEnergia", leituraRepository.findTopByTipoOrderByTimestampDesc("energia"));
        dados.put("ultimoGas", leituraRepository.findTopByTipoOrderByTimestampDesc("gas"));
        
        // Histórico últimas 24h
        LocalDateTime umDiaAtras = LocalDateTime.now().minusDays(1);
        dados.put("historicoAgua", leituraRepository.findByTipoAndTimestampBetween("agua", umDiaAtras, LocalDateTime.now()));
        dados.put("historicoEnergia", leituraRepository.findByTipoAndTimestampBetween("energia", umDiaAtras, LocalDateTime.now()));
        dados.put("historicoGas", leituraRepository.findByTipoAndTimestampBetween("gas", umDiaAtras, LocalDateTime.now()));
        
        // Alertas ativos
        dados.put("alertas", alertaService.getAlertasAtivos());
        
        // Dicas personalizadas
        dados.put("dicas", dicasService.gerarDicasPersonalizadas());
        
        return dados;
    }
    
    @GetMapping("/alertas")
    public Map<String, Object> getAlertas() {
        Map<String, Object> response = new HashMap<>();
        response.put("alertas", alertaService.getAlertasAtivos());
        return response;
    }
    
    @PostMapping("/alertas/{id}/resolver")
    public void resolverAlerta(@PathVariable Long id) {
        alertaService.resolverAlerta(id);
    }
}