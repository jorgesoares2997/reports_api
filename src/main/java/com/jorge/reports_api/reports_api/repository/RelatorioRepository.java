package com.jorge.reports_api.reports_api.repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.jorge.reports_api.reports_api.model.Relatorio;

public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {
}