package com.iot.simulator;

import com.iot.simulator.sensor.*;
import com.iot.simulator.mqtt.MqttPublisher;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Classe principal do simulador IoT
 * Inicia todos os sensores e o publisher MQTT
 */

public class Main {
    
    // Configurações
    private static final String BROKER_URL = "tcp://localhost:1883";
    private static final String CLIENT_ID = "simulador-java-" + System.currentTimeMillis();
    private static final long INTERVALO_SENSORES_MS = 5000; // 5 segundos
    
    private static volatile boolean executando = true;
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║     SIMULADOR IoT RESIDENCIAL - ÁGUA, ENERGIA e GÁS      ║");
        System.out.println("║                  Iniciando sistema...                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // SOLUÇÃO: Declarar publisher como final (não será reatribuída)
        final MqttPublisher[] publisherHolder = new MqttPublisher[1];
        
        try {
            // 1. Inicializa o publisher MQTT
            MqttPublisher publisher = new MqttPublisher(BROKER_URL, CLIENT_ID);
            publisher.connect();
            publisherHolder[0] = publisher;
            
            // 2. Cria os sensores
            AguaSensor aguaSensor = new AguaSensor("SENSOR_AGUA_001");
            EnergiaSensor energiaSensor = new EnergiaSensor("SENSOR_ENERGIA_001");
            GasSensor gasSensor = new GasSensor("SENSOR_GAS_001");
            
            // 3. Configura o callback para publicar os dados via MQTT
            aguaSensor.setOnDataCallback(data -> {
                try {
                    if (publisherHolder[0] != null && publisherHolder[0].isConectado()) {
                        publisherHolder[0].publish(data, aguaSensor.getTopicoMqtt());
                    }
                } catch (MqttException e) {
                    System.err.println("❌ Erro ao publicar água: " + e.getMessage());
                }
            });
            
            energiaSensor.setOnDataCallback(data -> {
                try {
                    if (publisherHolder[0] != null && publisherHolder[0].isConectado()) {
                        publisherHolder[0].publish(data, energiaSensor.getTopicoMqtt());
                    }
                } catch (MqttException e) {
                    System.err.println("❌ Erro ao publicar energia: " + e.getMessage());
                }
            });
            
            gasSensor.setOnDataCallback(data -> {
                try {
                    if (publisherHolder[0] != null && publisherHolder[0].isConectado()) {
                        publisherHolder[0].publish(data, gasSensor.getTopicoMqtt());
                    }
                } catch (MqttException e) {
                    System.err.println("❌ Erro ao publicar gás: " + e.getMessage());
                }
            });
            
            // 4. Inicia os sensores
            System.out.println("\n🚀 Iniciando sensores...\n");
            aguaSensor.iniciar(INTERVALO_SENSORES_MS);
            energiaSensor.iniciar(INTERVALO_SENSORES_MS);
            gasSensor.iniciar(INTERVALO_SENSORES_MS);
            
            // 5. Exibe instruções
            System.out.println("\n╔══════════════════════════════════════════════════════════╗");
            System.out.println("║  ✅ SISTEMA EM EXECUÇÃO                                 ║");
            System.out.println("║  📡 Dados sendo publicados via MQTT                     ║");
            System.out.println("║  🔥 Digite 'vazar' no console para simular vazamento    ║");
            System.out.println("║  🔥 Digite 'normal' para voltar ao normal               ║");
            System.out.println("║  🛑 Digite 'sair' para encerrar o simulador             ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝\n");
            
            // Loop principal - mantém o programa rodando até o usuário digitar 'sair'
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            while (executando) {
                if (scanner.hasNextLine()) {
                    String comando = scanner.nextLine().toLowerCase();
                    switch (comando) {
                        case "vazar":
                            gasSensor.ativarSimulacaoVazamento();
                            break;
                        case "normal":
                            gasSensor.desativarSimulacaoVazamento();
                            break;
                        case "sair":
                            System.out.println("\n🛑 Encerrando simulador...");
                            executando = false;
                            break;
                        default:
                            if (!comando.isEmpty()) {
                                System.out.println("Comando desconhecido. Opções: vazar, normal, sair");
                            }
                    }
                }
            }
            scanner.close();
            
            // 7. Encerramento
            aguaSensor.parar();
            energiaSensor.parar();
            gasSensor.parar();
            
            if (publisherHolder[0] != null) {
                publisherHolder[0].disconnect();
            }
            
            System.out.println("\n✅ Simulador encerrado com sucesso!");
            
        } catch (Exception e) {
            System.err.println("\n❌ ERRO FATAL: " + e.getMessage());
            e.printStackTrace();
            
            // Tenta desconectar em caso de erro
            if (publisherHolder[0] != null) {
                try {
                    publisherHolder[0].disconnect();
                } catch (MqttException ex) {
                    // Ignora
                }
            }
            System.exit(1);
        }
    }
}