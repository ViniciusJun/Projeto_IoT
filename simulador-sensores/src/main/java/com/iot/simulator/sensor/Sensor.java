package com.iot.simulator.sensor;

import com.iot.simulator.SensorData;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Classe abstrata base para todos os sensores simulados
 */
public abstract class Sensor {
    protected String id;
    protected String tipo;
    protected String topicoMqtt;
    protected String unidade;
    protected Random random;
    protected ScheduledExecutorService scheduler;
    protected boolean ativo;
    protected Consumer<SensorData> onDataCallback;
    
    public Sensor(String id, String tipo, String topicoMqtt, String unidade) {
        this.id = id;
        this.tipo = tipo;
        this.topicoMqtt = topicoMqtt;
        this.unidade = unidade;
        this.random = new Random();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.ativo = true;
    }
    
    /**
     * Método abstrato que cada sensor implementa com sua lógica específica
     * @return valor simulado do sensor
     */
    protected abstract double calcularValor();
    
    /**
     * Verifica se o valor atual gera um alerta
     * @param valor valor lido
     * @return true se gerar alerta
     */
    protected abstract boolean verificarAlerta(double valor);
    
    /**
     * Gera uma leitura completa do sensor
     */
    public SensorData gerarLeitura() {
        double valor = calcularValor();
        boolean alerta = verificarAlerta(valor);
        SensorData data = new SensorData(id, tipo, valor, unidade);
        if (alerta) {
            data.setAlerta(true);
        }
        return data;
    }
    
    /**
     * Inicia a geração periódica de dados
     * @param intervaloMs intervalo em milissegundos
     */
    public void iniciar(long intervaloMs) {
        scheduler.scheduleAtFixedRate(() -> {
            if (ativo && onDataCallback != null) {
                SensorData data = gerarLeitura();
                onDataCallback.accept(data);
            }
        }, 0, intervaloMs, TimeUnit.MILLISECONDS);
        
        System.out.println("✅ Sensor " + tipo + " (" + id + ") iniciado - Intervalo: " + intervaloMs + "ms");
    }
    
    /**
     * Para a geração de dados
     */
    public void parar() {
        ativo = false;
        scheduler.shutdown();
        System.out.println("⏹️ Sensor " + tipo + " (" + id + ") parado");
    }
    
    /**
     * Define o callback chamado quando um dado é gerado
     */
    public void setOnDataCallback(Consumer<SensorData> callback) {
        this.onDataCallback = callback;
    }
    
    // Getters
    public String getId() { return id; }
    public String getTipo() { return tipo; }
    public String getTopicoMqtt() { return topicoMqtt; }
    public String getUnidade() { return unidade; }
    public boolean isAtivo() { return ativo; }
}