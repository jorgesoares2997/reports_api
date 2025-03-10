package com.jorge.reports_api.reports_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jorge.reports_api.reports_api.model.Relatorio;
import com.jorge.reports_api.reports_api.service.RelatorioService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @PostMapping
    public ResponseEntity<Relatorio> criarRelatorio(@RequestBody Relatorio relatorio) {
        Relatorio novoRelatorio = relatorioService.criarRelatorio(relatorio);
        return ResponseEntity.ok(novoRelatorio);
    }
}