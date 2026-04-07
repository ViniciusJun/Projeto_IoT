package com.iot.backend.service;

import com.google.gson.Gson;
import com.iot.backend.model.Leitura;
import com.iot.backend.repository.LeituraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MqttConsumerService {
    
    @Autowired
    private LeituraRepository leituraRepository;
    
    @Autowired
    private AlertaService alertaService;
    
    @Autowired
    private WebSocketService webSocketService;
    
    private final Gson gson = new Gson();
    
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void processarMensagem(Message<String> message) {
        String payload = message.getPayload();
        String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
        
        try {
            // Parse do JSON recebido do simulador
            Map<String, Object> dados = gson.fromJson(payload, Map.class);
            
            String sensorId = (String) dados.get("sensorId");
            String tipo = (String) dados.get("tipo");
            Double valor = ((Number) dados.get("valor")).doubleValue();
            String unidade = (String) dados.get("unidade");
            boolean alertaOrigem = Boolean.TRUE.equals(dados.get("alerta"));
            
            // Cria e salva a leitura
            Leitura leitura = new Leitura(sensorId, tipo, valor, unidade, alertaOrigem);
            leitura = leituraRepository.save(leitura);
            
            System.out.printf(" Leitura salva: %s - %.2f %s%n", tipo, valor, unidade);
            
            // Verifica regras de alerta adicionais no backend
            alertaService.verificarRegras(tipo, valor);
            
            // Envia via WebSocket para o dashboard
            webSocketService.enviarLeitura(leitura);
            
        } catch (Exception e) {
            System.err.println(" Erro ao processar mensagem MQTT: " + e.getMessage());
        }
    }
}