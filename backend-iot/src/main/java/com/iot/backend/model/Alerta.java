package main.java.com.iot.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "alertas")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Alerta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String tipo; // agua, energia, gas
    
    @Column(nullable = false)
    private String severidade; // BAIXA, MEDIA, ALTA, CRITICA
    
    @Column(nullable = false, length = 500)
    private String mensagem;
    
    private Double valorRegistrado;
    private Double limite;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    private boolean resolvido;
    private LocalDateTime resolvidoEm;
    
    public Alerta(String tipo, String severidade, String mensagem, Double valorRegistrado, Double limite) {
        this.tipo = tipo;
        this.severidade = severidade;
        this.mensagem = mensagem;
        this.valorRegistrado = valorRegistrado;
        this.limite = limite;
        this.timestamp = LocalDateTime.now();
        this.resolvido = false;
    }
}