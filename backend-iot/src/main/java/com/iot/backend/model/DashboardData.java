package com.iot.backend.model;

import java.util.List;
import java.util.Map;

/**
 * DTO (Data Transfer Object) para enviar dados agregados ao dashboard
 */
public class DashboardData {
    
    private Leitura ultimaAgua;
    private Leitura ultimaEnergia;
    private Leitura ultimoGas;
    
    private List<Leitura> historicoAgua;
    private List<Leitura> historicoEnergia;
    private List<Leitura> historicoGas;
    
    private List<Alerta> alertas;
    private List<Map<String, Object>> dicas;
    private List<Modulo> modulos;
    
    private Double consumoAguaDiario;
    private Double consumoEnergiaDiario;
    private Double consumoGasDiario;
    
    private Double consumoAguaMensal;
    private Double consumoEnergiaMensal;
    private Double consumoGasMensal;
    
    private String ultimaAtualizacao;
    
    // Construtores
    public DashboardData() {}
    
    // Getters e Setters
    public Leitura getUltimaAgua() { return ultimaAgua; }
    public void setUltimaAgua(Leitura ultimaAgua) { this.ultimaAgua = ultimaAgua; }
    
    public Leitura getUltimaEnergia() { return ultimaEnergia; }
    public void setUltimaEnergia(Leitura ultimaEnergia) { this.ultimaEnergia = ultimaEnergia; }
    
    public Leitura getUltimoGas() { return ultimoGas; }
    public void setUltimoGas(Leitura ultimoGas) { this.ultimoGas = ultimoGas; }
    
    public List<Leitura> getHistoricoAgua() { return historicoAgua; }
    public void setHistoricoAgua(List<Leitura> historicoAgua) { this.historicoAgua = historicoAgua; }
    
    public List<Leitura> getHistoricoEnergia() { return historicoEnergia; }
    public void setHistoricoEnergia(List<Leitura> historicoEnergia) { this.historicoEnergia = historicoEnergia; }
    
    public List<Leitura> getHistoricoGas() { return historicoGas; }
    public void setHistoricoGas(List<Leitura> historicoGas) { this.historicoGas = historicoGas; }
    
    public List<Alerta> getAlertas() { return alertas; }
    public void setAlertas(List<Alerta> alertas) { this.alertas = alertas; }
    
    public List<Map<String, Object>> getDicas() { return dicas; }
    public void setDicas(List<Map<String, Object>> dicas) { this.dicas = dicas; }
    
    public List<Modulo> getModulos() { return modulos; }
    public void setModulos(List<Modulo> modulos) { this.modulos = modulos; }
    
    public Double getConsumoAguaDiario() { return consumoAguaDiario; }
    public void setConsumoAguaDiario(Double consumoAguaDiario) { this.consumoAguaDiario = consumoAguaDiario; }
    
    public Double getConsumoEnergiaDiario() { return consumoEnergiaDiario; }
    public void setConsumoEnergiaDiario(Double consumoEnergiaDiario) { this.consumoEnergiaDiario = consumoEnergiaDiario; }
    
    public Double getConsumoGasDiario() { return consumoGasDiario; }
    public void setConsumoGasDiario(Double consumoGasDiario) { this.consumoGasDiario = consumoGasDiario; }
    
    public Double getConsumoAguaMensal() { return consumoAguaMensal; }
    public void setConsumoAguaMensal(Double consumoAguaMensal) { this.consumoAguaMensal = consumoAguaMensal; }
    
    public Double getConsumoEnergiaMensal() { return consumoEnergiaMensal; }
    public void setConsumoEnergiaMensal(Double consumoEnergiaMensal) { this.consumoEnergiaMensal = consumoEnergiaMensal; }
    
    public Double getConsumoGasMensal() { return consumoGasMensal; }
    public void setConsumoGasMensal(Double consumoGasMensal) { this.consumoGasMensal = consumoGasMensal; }
    
    public String getUltimaAtualizacao() { return ultimaAtualizacao; }
    public void setUltimaAtualizacao(String ultimaAtualizacao) { this.ultimaAtualizacao = ultimaAtualizacao; }
}