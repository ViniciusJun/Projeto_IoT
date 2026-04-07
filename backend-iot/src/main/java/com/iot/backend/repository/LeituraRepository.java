package com.iot.backend.repository;

import com.iot.backend.model.Leitura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface LeituraRepository extends JpaRepository<Leitura, Long> {
    
    List<Leitura> findByTipoOrderByTimestampDesc(String tipo);
    
    List<Leitura> findByTipoAndTimestampBetween(String tipo, LocalDateTime inicio, LocalDateTime fim);
    
    Leitura findTopByTipoOrderByTimestampDesc(String tipo);
    
    @Query("SELECT AVG(l.valor) FROM Leitura l WHERE l.tipo = :tipo AND l.timestamp > :inicio")
    Double calcularMediaRecente(@Param("tipo") String tipo, @Param("inicio") LocalDateTime inicio);
    
    @Query("SELECT l FROM Leitura l WHERE l.tipo = :tipo AND l.timestamp > :inicio ORDER BY l.timestamp DESC")
    List<Leitura> findUltimasLeituras(@Param("tipo") String tipo, @Param("inicio") LocalDateTime inicio);
}