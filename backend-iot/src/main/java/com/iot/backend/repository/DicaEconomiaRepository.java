package com.iot.backend.repository;

import com.iot.backend.model.DicaEconomia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DicaEconomiaRepository extends JpaRepository<DicaEconomia, Long> {
    List<DicaEconomia> findByCategoriaOrderByOrdem(String categoria);
}