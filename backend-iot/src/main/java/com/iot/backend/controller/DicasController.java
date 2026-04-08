package com.iot.backend.controller;

import com.iot.backend.service.DicasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dicas")
@CrossOrigin(origins = "*")
public class DicasController {
    
    @Autowired
    private DicasService dicasService;
    
    @GetMapping
    public List<Map<String, Object>> getDicas() {
        return dicasService.gerarDicasPersonalizadas();
    }
}