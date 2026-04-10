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
        
        // Verificar consumo de água
        LocalDateTime umDiaAtras = LocalDateTime.now().minusDays(1);
        List<Leitura> leiturasAgua = leituraRepository.findUltimasLeituras("agua", umDiaAtras);
        
        double consumoMedioAgua = leiturasAgua.stream()
            .mapToDouble(Leitura::getValor)
            .average()
            .orElse(0);
        
        if (consumoMedioAgua > 10) {
            Map<String, Object> dica = new HashMap<>();
            dica.put("categoria", "AGUA");
            dica.put("titulo", "Reduza o consumo de água");
            dica.put("descricao", "Seu consumo médio está alto (" + String.format("%.1f", consumoMedioAgua) + 
                " L/min). Tome banhos mais curtos e feche torneiras.");
            dica.put("economiaEstimada", "Até 30% na conta");
            dicasPersonalizadas.add(dica);
        }
        
        // Dica de banho
        Map<String, Object> dicaBanho = new HashMap<>();
        dicaBanho.put("categoria", "AGUA");
        dicaBanho.put("titulo", "Tempo de banho ideal");
        dicaBanho.put("descricao", "Banhos de até 8 minutos economizam água e energia.");
        dicaBanho.put("economiaEstimada", "~720 litros/mês");
        dicasPersonalizadas.add(dicaBanho);
        
        // Dica de energia
        Map<String, Object> dicaEnergia = new HashMap<>();
        dicaEnergia.put("categoria", "ENERGIA");
        dicaEnergia.put("titulo", "Desligue aparelhos da tomada");
        dicaEnergia.put("descricao", "Aparelhos em stand-by consomem energia. Tire da tomada!");
        dicaEnergia.put("economiaEstimada", "~15% na conta de luz");
        dicasPersonalizadas.add(dicaEnergia);
        
        // Dica de gás
        Map<String, Object> dicaGas = new HashMap<>();
        dicaGas.put("categoria", "GAS");
        dicaGas.put("titulo", "Segurança com gás");
        dicaGas.put("descricao", "Verifique mangueiras regularmente e mantenha ambiente arejado.");
        dicaGas.put("economiaEstimada", "Prevenção de acidentes");
        dicasPersonalizadas.add(dicaGas);
        
        return dicasPersonalizadas;
    }
    
    public void carregarDicasPadrao() {
        if (dicaRepository.count() == 0) {
            dicaRepository.save(new DicaEconomia("AGUA", "Conserte vazamentos", 
                "Um pequeno vazamento pode desperdiçar milhares de litros por mês.", "🔧", 1));
            dicaRepository.save(new DicaEconomia("ENERGIA", "Use LED", 
                "Lâmpadas LED consomem até 80% menos energia.", "💡", 1));
            dicaRepository.save(new DicaEconomia("GAS", "Ventile o ambiente", 
                "Mantenha a cozinha arejada ao usar gás.", "💨", 1));
        }
    }
}