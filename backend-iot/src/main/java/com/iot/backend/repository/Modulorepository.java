package com.iot.backend.repository;

import com.iot.backend.model.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ModuloRepository extends JpaRepository<Modulo, Long> {
    
    Optional<Modulo> findByNome(String nome);
}
