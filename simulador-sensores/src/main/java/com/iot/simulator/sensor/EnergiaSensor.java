package com.iot.simulator.sensor;

import java.time.LocalTime;

/**
 * Simula um sensor de consumo elétrico (SCT-013 ou PZEM-004T)
 * Mede potência em Watts (W)
 */
public class EnergiaSensor extends Sensor {
    
    private static final double SOBRECARGA_LIMITE = 3000.0;  // Watts
    private static final double CONSUMO_ALTO_LIMITE = 2000.0; // Watts
    
    public EnergiaSensor(String id) {
        super(id, "energia", "casa/sensores/energia", "W");
    }
    
    @Override
    protected double calcularValor() {
        LocalTime agora = LocalTime.now();
        double potencia;
        
        // Define padrões de consumo baseados no horário
        if (isManha(agora)) {
            // 06:00 - 09:00: Café, chuveiro elétrico
            potencia = 800 + random.nextDouble() * 500;
            // Adiciona chuveiro nos horários de banho
            if (agora.getHour() == 7 || agora.getHour() == 8) {
                potencia += 4000 + random.nextDouble() * 500;
            }
        }
        else if (isTarde(agora)) {
            // 14:00 - 18:00: Uso moderado
            potencia = 300 + random.nextDouble() * 400;
        }
        else if (isNoite(agora)) {
            // 18:00 - 23:00: Pico de consumo
            potencia = 500 + random.nextDouble() * 800;
            // Ar condicionado no verão (simulado)
            if (agora.getHour() >= 19 && agora.getHour() <= 21) {
                potencia += 1200 + random.nextDouble() * 400;
            }
            // TV e computador
            potencia += 300 + random.nextDouble() * 200;
        }
        else if (isMadrugada(agora)) {
            // 23:00 - 06:00: Stand-by, geladeira
            potencia = 80 + random.nextDouble() * 50;
        }
        else {
            // Horário comercial (09:00 - 14:00)
            potencia = 200 + random.nextDouble() * 300;
        }
        
        // Adiciona ruído
        double ruido = random.nextGaussian() * 30;
        potencia = Math.max(0, potencia + ruido);
        
        // Arredonda para inteiro
        return Math.round(potencia);
    }
    
    @Override
    protected boolean verificarAlerta(double valor) {
        // Alerta 1: Sobrecarga elétrica
        if (valor > SOBRECARGA_LIMITE) {
            System.err.println(" ALERTA CRÍTICO: Sobrecarga elétrica detectada! " + valor + " W");
            return true;
        }
        
        // Alerta 2: Consumo muito alto por período prolongado
        if (valor > CONSUMO_ALTO_LIMITE && !isMadrugada(LocalTime.now())) {
            System.err.println(" ALERTA: Consumo elétrico elevado! " + valor + " W");
            return true;
        }
        
        return false;
    }
    
    private boolean isManha(LocalTime time) {
        return time.isAfter(LocalTime.of(6, 0)) && time.isBefore(LocalTime.of(9, 0));
    }
    
    private boolean isTarde(LocalTime time) {
        return time.isAfter(LocalTime.of(14, 0)) && time.isBefore(LocalTime.of(18, 0));
    }
    
    private boolean isNoite(LocalTime time) {
        return time.isAfter(LocalTime.of(18, 0)) && time.isBefore(LocalTime.of(23, 0));
    }
    
    private boolean isMadrugada(LocalTime time) {
        return time.isAfter(LocalTime.of(23, 0)) || time.isBefore(LocalTime.of(6, 0));
    }
}