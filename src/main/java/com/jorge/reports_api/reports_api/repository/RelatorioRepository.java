package com.jorge.reports_api.reports_api.repository;

import com.jorge.reports_api.reports_api.model.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {
    List<Relatorio> findByNomeVoluntario(String nomeVoluntario);
}