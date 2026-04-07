package com.iot.backend.repository;

import com.iot.backend.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    
    List<Alerta> findByResolvidoFalseOrderByTimestampDesc();
    
    List<Alerta> findByTipoAndResolvidoFalse(String tipo);
    
    List<Alerta> findBySeveridadeAndResolvidoFalse(String severidade);
}
