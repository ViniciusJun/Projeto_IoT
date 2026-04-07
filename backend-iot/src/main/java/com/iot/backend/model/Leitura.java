package com.iot.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "leituras")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Leitura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String sensorId;
    
    @Column(nullable = false)
    private String tipo; // agua, energia, gas
    
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
    
    // Construtor para leituras simples
    public Leitura(String sensorId, String tipo, Double valor, String unidade, boolean alerta) {
        this.sensorId = sensorId;
        this.tipo = tipo;
        this.valor = valor;
        this.unidade = unidade;
        this.alerta = alerta;
        this.timestamp = LocalDateTime.now();
    }
}