package com.iot.simulator.sensor;

import java.time.LocalTime;

/**
 * Simula um sensor de gás (MQ-2 / MQ-5)
 * Mede concentração em partes por milhão (ppm)
 */
public class GasSensor extends Sensor {
    
    private static final double ALERTA_AMARELO = 100.0;   // ppm - Atenção
    private static final double ALERTA_VERMELHO = 200.0;  // ppm - Perigo
    
    private boolean modoVazamentoAtivo = false;
    
    public GasSensor(String id) {
        super(id, "gas", "casa/sensores/gas", "ppm");
    }
    
    /**
     * Ativa simulação de vazamento de gás (para testes)
     */
    public void ativarSimulacaoVazamento() {
        this.modoVazamentoAtivo = true;
        System.err.println("🔥🔥🔥 SIMULAÇÃO: VAZAMENTO DE GÁS ATIVADO! 🔥🔥🔥");
    }
    
    /**
     * Desativa simulação de vazamento
     */
    public void desativarSimulacaoVazamento() {
        this.modoVazamentoAtivo = false;
        System.out.println("✅ SIMULAÇÃO: Vazamento de gás desativado");
    }
    
    @Override
    protected double calcularValor() {
        // Se modo vazamento estiver ativo, retorna concentração perigosa
        if (modoVazamentoAtivo) {
            return 500 + random.nextDouble() * 300;
        }
        
        LocalTime agora = LocalTime.now();
        double concentracao;
        
        // Define padrões de uso do gás
        if (isHorarioCafe(agora)) {
            // 06:00 - 08:00: Café da manhã
            concentracao = 20 + random.nextDouble() * 30;
        }
        else if (isHorarioAlmoco(agora)) {
            // 11:30 - 13:30: Almoço
            concentracao = 50 + random.nextDouble() * 40;
        }
        else if (isHorarioJantar(agora)) {
            // 18:30 - 20:30: Jantar
            concentracao = 60 + random.nextDouble() * 50;
        }
        else {
            // Período sem uso (apenas concentração residual)
            concentracao = 5 + random.nextDouble() * 10;
        }
        
        // Adiciona pequeno ruído
        double ruido = random.nextGaussian() * 3;
        concentracao = Math.max(0, concentracao + ruido);
        
        return Math.round(concentracao * 10) / 10.0;
    }
    
    @Override
    protected boolean verificarAlerta(double valor) {
        // Alerta 1: Nível perigoso (vermelho)
        if (valor > ALERTA_VERMELHO) {
            System.err.println("🔥🔴 ALERTA CRÍTICO: VAZAMENTO DE GÁS DETECTADO! " + valor + " ppm");
            System.err.println("🔴 AÇÃO: Evacue o local e não acione interruptores!");
            return true;
        }
        
        // Alerta 2: Nível de atenção (amarelo)
        if (valor > ALERTA_AMARELO) {
            System.err.println("⚠️🟡 ATENÇÃO: Nível elevado de gás! " + valor + " ppm");
            System.err.println("🟡 AÇÃO: Verifique se o fogão está desligado e ventile o ambiente");
            return true;
        }
        
        return false;
    }
    
    private boolean isHorarioCafe(LocalTime time) {
        return time.isAfter(LocalTime.of(6, 0)) && time.isBefore(LocalTime.of(8, 0));
    }
    
    private boolean isHorarioAlmoco(LocalTime time) {
        return time.isAfter(LocalTime.of(11, 30)) && time.isBefore(LocalTime.of(13, 30));
    }
    
    private boolean isHorarioJantar(LocalTime time) {
        return time.isAfter(LocalTime.of(18, 30)) && time.isBefore(LocalTime.of(20, 30));
    }
}