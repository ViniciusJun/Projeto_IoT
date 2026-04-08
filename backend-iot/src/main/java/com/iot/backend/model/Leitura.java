package com.iot.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "leituras")
public class Leitura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String sensorId;
    
    @Column(nullable = false)
    private String tipo;
    
    @Column(nullable = false)
    private Double valor;
    
    @Column(nullable = false)
    private String unidade;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    @Column(name = "consumo_diario")
    private Double consumoDiario;
    
    @Column(name = "consumo_mensal")
    private Double consumoMensal;
    
    private boolean alerta;
    
    @Column(length = 500)
    private String observacao;
    
    // Construtores
    public Leitura() {}
    
    public Leitura(String sensorId, String tipo, Double valor, String unidade, boolean alerta) {
        this.sensorId = sensorId;
        this.tipo = tipo;
        this.valor = valor;
        this.unidade = unidade;
        this.alerta = alerta;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    
    public String getUnidade() { return unidade; }
    public void setUnidade(String unidade) { this.unidade = unidade; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public Double getConsumoDiario() { return consumoDiario; }
    public void setConsumoDiario(Double consumoDiario) { this.consumoDiario = consumoDiario; }
    
    public Double getConsumoMensal() { return consumoMensal; }
    public void setConsumoMensal(Double consumoMensal) { this.consumoMensal = consumoMensal; }
    
    public boolean isAlerta() { return alerta; }
    public void setAlerta(boolean alerta) { this.alerta = alerta; }
    
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}