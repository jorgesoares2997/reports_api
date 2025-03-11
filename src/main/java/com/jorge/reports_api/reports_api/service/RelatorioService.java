package com.jorge.reports_api.reports_api.service;

import com.jorge.reports_api.reports_api.model.Relatorio;
import com.jorge.reports_api.reports_api.repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    // Criar relatório
    public Relatorio criarRelatorio(Relatorio relatorio) {
        String nomeVoluntario = SecurityContextHolder.getContext().getAuthentication().getName();
        relatorio.setNomeVoluntario(nomeVoluntario);
        return relatorioRepository.save(relatorio);
    }

    // Listar todos os relatórios
    public List<Relatorio> listarTodosRelatorios() {
        return relatorioRepository.findAll();
    }

    // Buscar relatório por ID
    public Optional<Relatorio> buscarRelatorioPorId(Long id) {
        return relatorioRepository.findById(id);
    }

    // Buscar relatórios por nome do voluntário
    public List<Relatorio> buscarRelatoriosPorVoluntario(String nomeVoluntario) {
        return relatorioRepository.findByNomeVoluntario(nomeVoluntario);
    }

    // Atualizar relatório
    public Relatorio atualizarRelatorio(Long id, Relatorio relatorioAtualizado) {
        Optional<Relatorio> relatorioExistente = relatorioRepository.findById(id);
        if (relatorioExistente.isPresent()) {
            Relatorio relatorio = relatorioExistente.get();
            relatorio.setDescricao(relatorioAtualizado.getDescricao());
            relatorio.setDataEscala(relatorioAtualizado.getDataEscala());
            relatorio.setEscalaId(relatorioAtualizado.getEscalaId());
            relatorio.setStatus(relatorioAtualizado.getStatus());
            // Não alteramos nomeVoluntario ou dataCriacao
            return relatorioRepository.save(relatorio);
        } else {
            throw new RuntimeException("Relatório com ID " + id + " não encontrado.");
        }
    }
}