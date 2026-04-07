package com.iot.backend.service;

import com.iot.backend.model.DicaEconomia;
import com.iot.backend.model.Leitura;
import com.iot.backend.repository.DicaEconomiaRepository;
import com.iot.backend.repository.LeituraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicasService {
    
    @Autowired
    private DicaEconomiaRepository dicaRepository;
    
    @Autowired
    private LeituraRepository leituraRepository;
    
    public List<Map<String, Object>> gerarDicasPersonalizadas() {
        List<Map<String, Object>> dicasPersonalizadas = new ArrayList<>();
        
        // Dicas baseadas em dados reais de consumo
        dicasPersonalizadas.addAll(gerarDicasAgua());
        dicasPersonalizadas.addAll(gerarDicasEnergia());
        dicasPersonalizadas.addAll(gerarDicasGas());
        
        return dicasPersonalizadas;
    }
    
    private List<Map<String, Object>> gerarDicasAgua() {
        List<Map<String, Object>> dicas = new ArrayList<>();
        
        // Verifica consumo médio de água nas últimas 24h
        LocalDateTime umDiaAtras = LocalDateTime.now().minusDays(1);
        List<Leitura> leiturasAgua = leituraRepository.findUltimasLeituras("agua", umDiaAtras);
        
        double consumoMedio = leiturasAgua.stream()
            .mapToDouble(Leitura::getValor)  // Agora funciona com getters manuais
            .average()
            .orElse(0);
        
        if (consumoMedio > 10) {
            Map<String, Object> dica = new HashMap<>();
            dica.put("categoria", "AGUA");
            dica.put("titulo", "💧 Reduza o consumo de água");
            dica.put("descricao", "Seu consumo médio está alto (" + String.format("%.1f", consumoMedio) + 
                " L/min). Experimente: tomar banhos mais curtos (máx 8 min), fechar torneira ao escovar os dentes, " +
                "consertar vazamentos e reutilizar água da máquina de lavar.");
            dica.put("economiaEstimada", "Até 30% na conta de água");
            dicas.add(dica);
        }
        
        // Dica sobre tempo de banho
        Map<String, Object> dicaBanho = new HashMap<>();
        dicaBanho.put("categoria", "AGUA");
        dicaBanho.put("titulo", "🚿 Tempo de banho ideal");
        dicaBanho.put("descricao", "Um banho de 8 minutos consome cerca de 64 litros de água. " +
            "Reduza para 5 minutos e economize 24 litros por banho!");
        dicaBanho.put("economiaEstimada", "~720 litros/mês por pessoa");
        dicas.add(dicaBanho);
        
        return dicas;
    }
    
    private List<Map<String, Object>> gerarDicasEnergia() {
        List<Map<String, Object>> dicas = new ArrayList<>();
        
        LocalDateTime umaHoraAtras = LocalDateTime.now().minusHours(1);
        Double consumoEnergia = leituraRepository.calcularMediaRecente("energia", umaHoraAtras);
        
        if (consumoEnergia != null && consumoEnergia > 500) {
            Map<String, Object> dica = new HashMap<>();
            dica.put("categoria", "ENERGIA");
            dica.put("titulo", "⚡ Economize energia elétrica");
            dica.put("descricao", "Seu consumo está elevado (" + String.format("%.0f", consumoEnergia) + 
                " W). Dicas: troque lâmpadas por LED, tire aparelhos da tomada em stand-by, " +
                "use ar condicionado a 23°C, tome banhos mais rápidos com chuveiro na posição verão.");
            dica.put("economiaEstimada", "Até 25% na conta de luz");
            dicas.add(dica);
        }
        
        // Dica sobre chuveiro elétrico
        Map<String, Object> dicaChuveiro = new HashMap<>();
        dicaChuveiro.put("categoria", "ENERGIA");
        dicaChuveiro.put("titulo", "🚿 Chuveiro elétrico: economia");
        dicaChuveiro.put("descricao", "Um banho de 15 minutos com chuveiro na posição 'inverno' consome ~4,5 kWh. " +
            "Na posição 'verão', o consumo cai para ~2,5 kWh. Economize até 45%!");
        dicaChuveiro.put("economiaEstimada", "~R$ 30/mês por banho diário");
        dicas.add(dicaChuveiro);
        
        return dicas;
    }
    
    private List<Map<String, Object>> gerarDicasGas() {
        List<Map<String, Object>> dicas = new ArrayList<>();
        
        Map<String, Object> dica = new HashMap<>();
        dica.put("categoria", "GAS");
        dica.put("titulo", " Segurança e economia com gás");
        dica.put("descricao", "Para evitar vazamentos e economizar: mantenha o botijão em local arejado, " +
            "verifique mangueiras a cada 6 meses, feche o registro após o uso, prefira panelas que aproveitam melhor o calor.");
        dica.put("economiaEstimada", "Até 15% na conta de gás");
        dicas.add(dica);
        
        return dicas;
    }
    
    public void carregarDicasPadrao() {
        if (dicaRepository.count() == 0) {
            dicaRepository.save(new DicaEconomia("AGUA", " Conserte vazamentos",
                "Um buraco de 2mm no encanamento pode desperdiçar até 3.200 litros de água por dia! " +
                "Verifique torneiras, válvulas de descarga e canos aparentes.", "", 1));
            
            dicaRepository.save(new DicaEconomia("AGUA", " Reaproveite água",
                "A água da máquina de lavar pode ser usada para lavar calçadas. A água do banho pode " +
                "ser reaproveitada para descarga do vaso sanitário.", "", 2));
            
            dicaRepository.save(new DicaEconomia("ENERGIA", " Aparelhos em stand-by",
                "Aparelhos em stand-by consomem até 15% da energia. Tire da tomada: TV, computador, " +
                "microondas e carregadores.", "", 1));
            
            dicaRepository.save(new DicaEconomia("ENERGIA", " Horário de ponta",
                "Evite usar equipamentos de alto consumo (chuveiro, ferro, ar condicionado) entre 18h e 21h, " +
                "horário de ponta onde a energia é mais cara.", "", 2));
            
            dicaRepository.save(new DicaEconomia("GAS", " Válvula de segurança",
                "Instale uma válvula de segurança no botijão de gás. Ela fecha automaticamente em caso " +
                "de vazamento ou superaquecimento.", "", 1));
        }
    }
}