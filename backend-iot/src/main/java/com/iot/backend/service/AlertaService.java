package com.iot.backend.service;

import com.iot.backend.model.Alerta;
import com.iot.backend.model.Leitura;
import com.iot.backend.repository.AlertaRepository;
import com.iot.backend.repository.LeituraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertaService {
    
    @Autowired
    private AlertaRepository alertaRepository;
    
    @Autowired
    private LeituraRepository leituraRepository;
    
    @Value("${alerta.agua.vazamento-noturno:2.0}")
    private double limiteVazamentoAguaNoturno;
    
    @Value("${alerta.agua.vazamento-maximo:20.0}")
    private double limiteVazamentoAguaMaximo;
    
    @Value("${alerta.energia.sobrecarga:3000}")
    private double limiteSobrecargaEnergia;
    
    @Value("${alerta.energia.pico-tensao:240}")
    private double limitePicoTensao;
    
    @Value("${alerta.gas.alerta-amarelo:100}")
    private double limiteGasAmarelo;
    
    @Value("${alerta.gas.alerta-vermelho:200}")
    private double limiteGasVermelho;
    
    public void verificarRegras(String tipo, double valor) {
        switch (tipo) {
            case "agua":
                verificarAgua(valor);
                break;
            case "energia":
                verificarEnergia(valor);
                break;
            case "gas":
                verificarGas(valor);
                break;
        }
    }
    
    private void verificarAgua(double vazao) {
        LocalDateTime agora = LocalDateTime.now();
        boolean isMadrugada = agora.getHour() >= 0 && agora.getHour() < 5;
        
        // Vazamento na madrugada
        if (isMadrugada && vazao > limiteVazamentoAguaNoturno) {
            criarAlerta("agua", "ALTA", 
                String.format(" Possível vazamento de água na madrugada! Vazão: %.1f L/min", vazao),
                vazao, limiteVazamentoAguaNoturno);
        }
        
        // Vazamento grave
        if (vazao > limiteVazamentoAguaMaximo) {
            criarAlerta("agua", "CRITICA",
                String.format(" VAZAMENTO GRAVE DE ÁGUA! Vazão: %.1f L/min", vazao),
                vazao, limiteVazamentoAguaMaximo);
        }
        
        // Consumo excessivo (verificar média)
        verificarConsumoExcessivo("agua", vazao);
    }
    
    private void verificarEnergia(double potencia) {
        // Sobrecarga elétrica
        if (potencia > limiteSobrecargaEnergia) {
            criarAlerta("energia", "CRITICA",
                String.format(" SOBRECARGA ELÉTRICA! Potência: %.0f W", potencia),
                potencia, limiteSobrecargaEnergia);
        }
        
        // Estresse da corrente (simulado - baseado em variação brusca)
        Leitura ultimaLeitura = leituraRepository.findTopByTipoOrderByTimestampDesc("energia");
        if (ultimaLeitura != null) {
            double variacao = Math.abs(potencia - ultimaLeitura.getValor());
            if (variacao > 1000) {
                criarAlerta("energia", "MEDIA",
                    String.format(" Variação brusca de consumo detectada! Variação: %.0f W", variacao),
                    variacao, 1000.0);
            }
        }
        
        // Pico de tensão (simulado - baseado em valores anômalos)
        if (potencia > 5000) {
            criarAlerta("energia", "ALTA",
                String.format(" PICO DE CONSUMO DETECTADO! Potência: %.0f W", potencia),
                potencia, 5000.0);
        }
    }
    
    private void verificarGas(double concentracao) {
        // Nível de atenção
        if (concentracao > limiteGasAmarelo && concentracao <= limiteGasVermelho) {
            criarAlerta("gas", "ALTA",
                String.format(" Nível elevado de gás! Concentração: %.1f ppm", concentracao),
                concentracao, limiteGasAmarelo);
        }
        
        // Vazamento crítico
        if (concentracao > limiteGasVermelho) {
            criarAlerta("gas", "CRITICA",
                String.format(" VAZAMENTO DE GÁS DETECTADO! Concentração: %.1f ppm - Evacue o local!", concentracao),
                concentracao, limiteGasVermelho);
        }
    }
    
    private void verificarConsumoExcessivo(String tipo, double valorAtual) {
        LocalDateTime umaHoraAtras = LocalDateTime.now().minusHours(1);
        Double mediaRecente = leituraRepository.calcularMediaRecente(tipo, umaHoraAtras);
        
        if (mediaRecente != null && valorAtual > mediaRecente * 1.5) {
            criarAlerta(tipo, "MEDIA",
                String.format(" Consumo excessivo detectado! Atual: %.1f | Média: %.1f", 
                    valorAtual, mediaRecente),
                valorAtual, mediaRecente * 1.5);
        }
    }
    
    private void criarAlerta(String tipo, String severidade, String mensagem, Double valor, Double limite) {
        Alerta alerta = new Alerta(tipo, severidade, mensagem, valor, limite);
        alertaRepository.save(alerta);
        System.err.println("🔔 ALERTA GERADO: " + mensagem);
    }
    
    public List<Alerta> getAlertasAtivos() {
        return alertaRepository.findByResolvidoFalseOrderByTimestampDesc();
    }
    
    public void resolverAlerta(Long id) {
        alertaRepository.findById(id).ifPresent(alerta -> {
            alerta.setResolvido(true);
            alerta.setResolvidoEm(LocalDateTime.now());
            alertaRepository.save(alerta);
        });
    }
}
