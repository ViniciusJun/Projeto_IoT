package com.iot.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alertas")
public class Alerta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String tipo;
    
    @Column(nullable = false)
    private String severidade;
    
    @Column(nullable = false, length = 500)
    private String mensagem;
    
    private Double valorRegistrado;
    private Double limite;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    private boolean resolvido;
    private LocalDateTime resolvidoEm;
    
    // Construtores
    public Alerta() {}
    
    public Alerta(String tipo, String severidade, String mensagem, Double valorRegistrado, Double limite) {
        this.tipo = tipo;
        this.severidade = severidade;
        this.mensagem = mensagem;
        this.valorRegistrado = valorRegistrado;
        this.limite = limite;
        this.timestamp = LocalDateTime.now();
        this.resolvido = false;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public String getSeveridade() { return severidade; }
    public void setSeveridade(String severidade) { this.severidade = severidade; }
    
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    
    public Double getValorRegistrado() { return valorRegistrado; }
    public void setValorRegistrado(Double valorRegistrado) { this.valorRegistrado = valorRegistrado; }
    
    public Double getLimite() { return limite; }
    public void setLimite(Double limite) { this.limite = limite; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public boolean isResolvido() { return resolvido; }
    public void setResolvido(boolean resolvido) { this.resolvido = resolvido; }
    
    public LocalDateTime getResolvidoEm() { return resolvidoEm; }
    public void setResolvidoEm(LocalDateTime resolvidoEm) { this.resolvidoEm = resolvidoEm; }
}