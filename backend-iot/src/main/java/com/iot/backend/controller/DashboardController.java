package com.iot.backend.controller;

import com.iot.backend.model.Alerta;
import com.iot.backend.model.DashboardData;
import com.iot.backend.model.Leitura;
import com.iot.backend.model.Modulo;
import com.iot.backend.repository.LeituraRepository;
import com.iot.backend.repository.ModuloRepository;
import com.iot.backend.service.AlertaService;
import com.iot.backend.service.DicasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    
    @Autowired
    private ModuloRepository moduloRepository;
    
    @GetMapping("/dados")
    public DashboardData getDadosDashboard() {
        DashboardData dados = new DashboardData();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        dados.setUltimaAtualizacao(LocalDateTime.now().format(formatter));
        
        // Últimas leituras
        dados.setUltimaAgua(leituraRepository.findTopByTipoOrderByTimestampDesc("agua"));
        dados.setUltimaEnergia(leituraRepository.findTopByTipoOrderByTimestampDesc("energia"));
        dados.setUltimoGas(leituraRepository.findTopByTipoOrderByTimestampDesc("gas"));
        
        // Histórico últimas 24h
        LocalDateTime umDiaAtras = LocalDateTime.now().minusDays(1);
        dados.setHistoricoAgua(leituraRepository.findByTipoAndTimestampBetween("agua", umDiaAtras, LocalDateTime.now()));
        dados.setHistoricoEnergia(leituraRepository.findByTipoAndTimestampBetween("energia", umDiaAtras, LocalDateTime.now()));
        dados.setHistoricoGas(leituraRepository.findByTipoAndTimestampBetween("gas", umDiaAtras, LocalDateTime.now()));
        
        // Alertas ativos
        dados.setAlertas(alertaService.getAlertasAtivos());
        
        // Dicas personalizadas
        dados.setDicas(dicasService.gerarDicasPersonalizadas());
        
        // Módulos
        dados.setModulos(moduloRepository.findAll());
        
        return dados;
    }
    
    @GetMapping("/alertas")
    public List<Alerta> getAlertas() {
        return alertaService.getAlertasAtivos();
    }
    
    @PostMapping("/alertas/{id}/resolver")
    public void resolverAlerta(@PathVariable Long id) {
        alertaService.resolverAlerta(id);
    }
}