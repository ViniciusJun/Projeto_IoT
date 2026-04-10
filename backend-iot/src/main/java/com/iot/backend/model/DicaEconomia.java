package com.iot.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dicas_economia")
public class DicaEconomia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String categoria;
    
    @Column(nullable = false, length = 500)
    private String titulo;
    
    @Column(nullable = false, length = 1000)
    private String descricao;
    
    private String icone;
    private int ordem;
    
    public DicaEconomia() {}
    
    public DicaEconomia(String categoria, String titulo, String descricao, String icone, int ordem) {
        this.categoria = categoria;
        this.titulo = titulo;
        this.descricao = descricao;
        this.icone = icone;
        this.ordem = ordem;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public String getIcone() { return icone; }
    public void setIcone(String icone) { this.icone = icone; }
    
    public int getOrdem() { return ordem; }
    public void setOrdem(int ordem) { this.ordem = ordem; }
}