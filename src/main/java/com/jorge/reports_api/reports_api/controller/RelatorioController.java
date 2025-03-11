package com.jorge.reports_api.reports_api.controller;

import com.jorge.reports_api.reports_api.model.Relatorio;
import com.jorge.reports_api.reports_api.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    // POST - Criar relatório
    @PostMapping
    public ResponseEntity<Relatorio> criarRelatorio(@RequestBody Relatorio relatorio) {
        Relatorio novoRelatorio = relatorioService.criarRelatorio(relatorio);
        return ResponseEntity.ok(novoRelatorio);
    }

    // GET - Listar todos os relatórios
    @GetMapping
    public ResponseEntity<List<Relatorio>> listarTodosRelatorios() {
        List<Relatorio> relatorios = relatorioService.listarTodosRelatorios();
        return ResponseEntity.ok(relatorios);
    }

    // GET - Buscar relatório por ID
    @GetMapping("/{id}")
    public ResponseEntity<Relatorio> buscarRelatorioPorId(@PathVariable Long id) {
        return relatorioService.buscarRelatorioPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET - Buscar relatórios por nome do voluntário
    @GetMapping("/voluntario/{nomeVoluntario}")
    public ResponseEntity<List<Relatorio>> buscarRelatoriosPorVoluntario(@PathVariable String nomeVoluntario) {
        List<Relatorio> relatorios = relatorioService.buscarRelatoriosPorVoluntario(nomeVoluntario);
        if (relatorios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(relatorios);
    }

    // PUT - Atualizar relatório
    @PutMapping("/{id}")
    public ResponseEntity<Relatorio> atualizarRelatorio(@PathVariable Long id,
            @RequestBody Relatorio relatorioAtualizado) {
        try {
            Relatorio relatorio = relatorioService.atualizarRelatorio(id, relatorioAtualizado);
            return ResponseEntity.ok(relatorio);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}