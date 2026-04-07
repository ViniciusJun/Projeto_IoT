package com.iot.backend.service;

import com.iot.backend.model.Leitura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebSocketService {
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    public void enviarLeitura(Leitura leitura) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", leitura.getId());
        payload.put("tipo", leitura.getTipo());
        payload.put("valor", leitura.getValor());
        payload.put("unidade", leitura.getUnidade());
        payload.put("timestamp", leitura.getTimestamp().toString());
        payload.put("alerta", leitura.isAlerta());
        
        messagingTemplate.convertAndSend("/topic/leituras", payload);
    }
    
    public void enviarAlerta(Map<String, Object> alerta) {
        messagingTemplate.convertAndSend("/topic/alertas", alerta);
    }
}