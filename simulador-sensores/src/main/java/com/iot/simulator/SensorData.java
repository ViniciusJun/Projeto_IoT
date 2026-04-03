package com.iot.simulator;

import com.google.gson.Gson;

/**
 * Representa os dados gerados por um sensor
 */
public class SensorData {
    private String sensorId;
    private String tipo;
    private double valor;
    private long timestamp;
    private boolean alerta;
    private String unidade;
    
    // Construtor completo
    public SensorData(String sensorId, String tipo, double valor, long timestamp, boolean alerta, String unidade) {
        this.sensorId = sensorId;
        this.tipo = tipo;
        this.valor = valor;
        this.timestamp = timestamp;
        this.alerta = alerta;
        this.unidade = unidade;
    }
    
    // Construtor simplificado (alerta = false)
    public SensorData(String sensorId, String tipo, double valor, String unidade) {
        this(sensorId, tipo, valor, System.currentTimeMillis(), false, unidade);
    }
    
    // Getters
    public String getSensorId() { return sensorId; }
    public String getTipo() { return tipo; }
    public double getValor() { return valor; }
    public long getTimestamp() { return timestamp; }
    public boolean isAlerta() { return alerta; }
    public String getUnidade() { return unidade; }
    
    // Setters
    public void setAlerta(boolean alerta) { this.alerta = alerta; }
    public void setValor(double valor) { this.valor = valor; }
    
    /**
     * Converte o objeto para JSON
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s: %.2f %s %s", 
            sensorId, tipo, valor, unidade, alerta ? "⚠️ ALERTA" : "");
    }
}