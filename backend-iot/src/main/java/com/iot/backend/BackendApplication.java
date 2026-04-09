package com.iot.backend;

import com.iot.backend.controller.ModuloController;
import com.iot.backend.service.DicasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {
    
    @Autowired
    private ModuloController moduloController;
    
    @Autowired
    private DicasService dicasService;
    
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║       BACKEND IoT RESIDENCIAL INICIADO COM SUCESSO!          ║");
        System.out.println("║                                                              ║");
        System.out.println("║    API disponível em: http://localhost:8080                  ║");
        System.out.println("║    Dashboard: http://localhost:8080                          ║");
        System.out.println("║    WebSocket: ws://localhost:8080/ws                         ║");
        System.out.println("║    H2 Console: http://localhost:8080/h2-console              ║");
        System.out.println("║                                                              ║");
        System.out.println("║    Comandos do simulador: vazar, normal, sair                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Inicializa módulos padrão
        moduloController.inicializarModulos();
        // Carrega dicas padrão
        dicasService.carregarDicasPadrao();
        System.out.println("✅ Módulos e dicas inicializados!");
    }
}