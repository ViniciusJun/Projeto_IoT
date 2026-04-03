package com.iot.simulator.sensor;

import java.time.LocalTime;

/**
 * Simula um sensor de fluxo de água (YF-S201)
 * Mede vazão em Litros por minuto (L/min)
 */
public class AguaSensor extends Sensor {
    
    private static final double VAZAMENTO_NOTURNO_LIMITE = 2.0;  // L/min
    private static final double VAZAMENTO_MAXIMO = 20.0;         // L/min
    
    public AguaSensor(String id) {
        super(id, "agua", "casa/sensores/agua", "L/min");
    }
    
    @Override
    protected double calcularValor() {
        LocalTime agora = LocalTime.now();
        double fluxoBase;
        
        // Define padrões de consumo baseados no horário
        if (isManhaCedo(agora)) {
            // 06:00 - 08:30: Banho, café da manhã
            fluxoBase = 8.0 + random.nextDouble() * 5;
        } 
        else if (isAlmoco(agora)) {
            // 12:00 - 14:00: Almoço, lavar louça
            fluxoBase = 5.0 + random.nextDouble() * 4;
        }
        else if (isNoite(agora)) {
            // 18:00 - 22:00: Banho, jantar, louça
            fluxoBase = 7.0 + random.nextDouble() * 6;
        }
        else if (isMadrugada(agora)) {
            // 23:00 - 05:00: Apenas pequenas perdas
            fluxoBase = 0.2 + random.nextDouble() * 0.5;
        }
        else {
            // Período normal durante o dia
            fluxoBase = 1.0 + random.nextDouble() * 2;
        }
        
        // Adiciona ruído gaussiano para simular variações naturais
        double ruido = random.nextGaussian() * 0.3;
        double fluxo = Math.max(0, fluxoBase + ruido);
        
        // Arredonda para 1 casa decimal
        return Math.round(fluxo * 10) / 10.0;
    }
    
    @Override
    protected boolean verificarAlerta(double valor) {
        LocalTime agora = LocalTime.now();
        
        // Alerta 1: Fluxo alto na madrugada (possível vazamento)
        if (isMadrugada(agora) && valor > VAZAMENTO_NOTURNO_LIMITE) {
            System.err.println("💧 ALERTA: Possível vazamento de água na madrugada! Fluxo: " + valor + " L/min");
            return true;
        }
        
        // Alerta 2: Fluxo extremamente alto (vazamento grave ou consumo excessivo)
        if (valor > VAZAMENTO_MAXIMO) {
            System.err.println("💧 ALERTA CRÍTICO: Fluxo de água muito elevado! " + valor + " L/min");
            return true;
        }
        
        return false;
    }
    
    // Métodos auxiliares para verificar horários
    private boolean isManhaCedo(LocalTime time) {
        return time.isAfter(LocalTime.of(6, 0)) && time.isBefore(LocalTime.of(8, 30));
    }
    
    private boolean isAlmoco(LocalTime time) {
        return time.isAfter(LocalTime.of(12, 0)) && time.isBefore(LocalTime.of(14, 0));
    }
    
    private boolean isNoite(LocalTime time) {
        return time.isAfter(LocalTime.of(18, 0)) && time.isBefore(LocalTime.of(22, 0));
    }
    
    private boolean isMadrugada(LocalTime time) {
        return time.isAfter(LocalTime.of(23, 0)) || time.isBefore(LocalTime.of(5, 0));
    }
}