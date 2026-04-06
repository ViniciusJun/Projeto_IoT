package main.java.com.iot.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dicas_economia")
@Data
@NoArgsConstructor

public class DicaEconomia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String categoria; // AGUA, ENERGIA, GAS
    
    @Column(nullable = false, length = 500)
    private String titulo;
    
    @Column(nullable = false, length = 1000)
    private String descricao;
    
    private String icone;
    private int ordem;
    
    public DicaEconomia(String categoria, String titulo, String descricao, String icone, int ordem) {
        this.categoria = categoria;
        this.titulo = titulo;
        this.descricao = descricao;
        this.icone = icone;
        this.ordem = ordem;
    }
}
