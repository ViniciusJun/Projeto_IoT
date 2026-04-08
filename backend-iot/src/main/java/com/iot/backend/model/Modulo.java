package com.iot.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "modulos")
public class Modulo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String nome;
    
    private boolean ativo;
    private String descricao;
    
    // Construtores
    public Modulo() {}
    
    public Modulo(String nome, boolean ativo, String descricao) {
        this.nome = nome;
        this.ativo = ativo;
        this.descricao = descricao;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}