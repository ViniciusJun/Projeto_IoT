package main.java.com.iot.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "modulos")
@Data
@NoArgsConstructor

public class Modulo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String nome; // AGUA, ENERGIA, GAS, ALERTAS, DICAS, NFC
    
    private boolean ativo;
    private String descricao;
    
    public Modulo(String nome, boolean ativo, String descricao) {
        this.nome = nome;
        this.ativo = ativo;
        this.descricao = descricao;
    }
}