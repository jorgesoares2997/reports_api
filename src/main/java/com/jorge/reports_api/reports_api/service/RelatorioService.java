package com.jorge.reports_api.reports_api.service;

import com.jorge.reports_api.reports_api.model.Relatorio;
import com.jorge.reports_api.reports_api.repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Autowired
    private EmailService emailService;

    // Criar relatório
    public Relatorio criarRelatorio(Relatorio relatorio) {
        String nomeVoluntario = SecurityContextHolder.getContext().getAuthentication().getName();
        relatorio.setNomeVoluntario(nomeVoluntario);
        Relatorio savedRelatorio = relatorioRepository.save(relatorio);

        // Formatar os detalhes do relatório para o e-mail
        String reportDetails = formatReportDetails(savedRelatorio);

        // Enviar e-mails
        try {
            // E-mail para o emissor (nomeVoluntario é o e-mail do usuário autenticado)
            emailService.sendReportEmail(
                    nomeVoluntario,
                    "Novo Relatório Criado - ID: " + savedRelatorio.getId(),
                    reportDetails);

            // E-mail para você (fixo)
            emailService.sendReportEmail(
                    "jorgesoares2997@gmail.com",
                    "Novo Relatório Criado - ID: " + savedRelatorio.getId(),
                    reportDetails);
        } catch (Exception e) {
            // Logar o erro, mas não interromper o fluxo principal
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
        }

        return savedRelatorio;
    }

    // Método para formatar os detalhes do relatório
    private String formatReportDetails(Relatorio relatorio) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "<h2>Detalhes do Relatório</h2>" +
                "<p><strong>ID:</strong> " + relatorio.getId() + "</p>" +
                "<p><strong>Descrição:</strong> " + relatorio.getDescricao() + "</p>" +
                "<p><strong>Voluntário:</strong> " + relatorio.getNomeVoluntario() + "</p>" +
                "<p><strong>Data da Escala:</strong> " + relatorio.getDataEscala().toString() + "</p>" +
                "<p><strong>Data de Criação:</strong> " + relatorio.getDataCriacao().format(formatter) + "</p>" +
                "<p><strong>Status:</strong> " + relatorio.getStatus() + "</p>" +
                "<p><strong>Escala ID:</strong> " + relatorio.getEscalaId() + "</p>";
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
            return relatorioRepository.save(relatorio);
        } else {
            throw new RuntimeException("Relatório com ID " + id + " não encontrado.");
        }
    }
}