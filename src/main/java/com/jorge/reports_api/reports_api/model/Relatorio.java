package com.jorge.reports_api.reports_api.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "relatorios")
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 5000) // Campo grande pra texto
    private String descricao;

    @Column(nullable = false)
    private String nomeVoluntario; // Vem da autenticação

    @Column(nullable = false)
    private LocalDate dataEscala; // Data da escala cumprida

    @Column(nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now(); // Data de submissão

    @Column(nullable = false)
    private String status = "PENDENTE"; // Pendente por padrão

    @Column(nullable = false)
    private Long escalaId; // Vincula a uma escala específica
}