package com.iot.simulator.mqtt;

import com.iot.simulator.SensorData;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Responsável por publicar os dados dos sensores no broker MQTT
 */
public class MqttPublisher {
    
    private String brokerUrl;
    private String clientId;
    private MqttClient client;
    private boolean conectado = false;
    
    public MqttPublisher(String brokerUrl, String clientId) {
        this.brokerUrl = brokerUrl;
        this.clientId = clientId;
    }
    
    /**
     * Conecta ao broker MQTT
     */
    public void connect() throws MqttException {
        try {
            client = new MqttClient(brokerUrl, clientId, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setAutomaticReconnect(true);
            options.setConnectionTimeout(30);
            options.setKeepAliveInterval(60);
            
            System.out.println("🔌 Conectando ao broker MQTT: " + brokerUrl);
            client.connect(options);
            conectado = true;
            System.out.println("✅ Conectado ao broker MQTT com sucesso!");
            
        } catch (MqttException e) {
            System.err.println("❌ Erro ao conectar ao broker MQTT: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Publica um dado em um tópico
     */
    public void publish(SensorData data, String topico) throws MqttException {
        if (!conectado || client == null || !client.isConnected()) {
            System.err.println("⚠️ Não é possível publicar: cliente desconectado");
            return;
        }
        
        String payload = data.toJson();
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(1);  // Qualidade de serviço: pelo menos uma vez
        message.setRetained(false);
        
        client.publish(topico, message);
        
        // Log com cores diferentes para alertas
        if (data.isAlerta()) {
            System.err.println("🔔 [PUBLISH ALERTA] " + topico + " -> " + data);
        } else {
            System.out.println("📡 [PUBLISH] " + topico + " -> " + data);
        }
    }
    
    /**
     * Desconecta do broker
     */
    public void disconnect() throws MqttException {
        if (client != null && client.isConnected()) {
            client.disconnect();
            conectado = false;
            System.out.println("🔌 Desconectado do broker MQTT");
        }
    }
    
    /**
     * Verifica se está conectado
     */
    public boolean isConectado() {
        return conectado && client != null && client.isConnected();
    }
}