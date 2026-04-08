package com.iot.backend.controller;

import com.iot.backend.model.Modulo;
import com.iot.backend.repository.ModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modulos")
@CrossOrigin(origins = "*")
public class ModuloController {
    
    @Autowired
    private ModuloRepository moduloRepository;
    
    @GetMapping
    public List<Modulo> getModulos() {
        return moduloRepository.findAll();
    }
    
    @PutMapping("/{nome}/toggle")
    public Modulo toggleModulo(@PathVariable String nome) {
        Modulo modulo = moduloRepository.findByNome(nome)
            .orElseThrow(() -> new RuntimeException("Módulo não encontrado"));
        modulo.setAtivo(!modulo.isAtivo());
        return moduloRepository.save(modulo);
    }
    
    @PostMapping("/init")
    public void inicializarModulos() {
        if (moduloRepository.count() == 0) {
            moduloRepository.save(new Modulo("AGUA", true, "Monitoramento de consumo de água"));
            moduloRepository.save(new Modulo("ENERGIA", true, "Monitoramento de consumo de energia"));
            moduloRepository.save(new Modulo("GAS", true, "Monitoramento de gás"));
            moduloRepository.save(new Modulo("ALERTAS", true, "Alertas de risco e vazamentos"));
            moduloRepository.save(new Modulo("DICAS", true, "Dicas de economia"));
            moduloRepository.save(new Modulo("NFC", true, "Acesso rápido via NFC"));
        }
    }
}