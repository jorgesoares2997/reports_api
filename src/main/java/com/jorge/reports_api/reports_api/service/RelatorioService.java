package com.jorge.reports_api.reports_api.service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.jorge.reports_api.reports_api.model.Relatorio;
import com.jorge.reports_api.reports_api.repository.RelatorioRepository;


@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    public Relatorio criarRelatorio(Relatorio relatorio) {
        // Pega o nome do voluntário da autenticação
        String nomeVoluntario = SecurityContextHolder.getContext().getAuthentication().getName();
        relatorio.setNomeVoluntario(nomeVoluntario);
        return relatorioRepository.save(relatorio);
    }
}