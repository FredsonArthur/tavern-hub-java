package com.tavernhub.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_mesa")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da mesa é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome da mesa deve ter entre 3 e 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O nome do mestre é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome do mestre deve ter entre 3 e 100 caracteres.")
    @Column(name = "mestre_nome", nullable = false, length = 100)
    private String mestreNome;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    // Construtor Padrão (Necessário para o JPA)
    public Mesa() {
    }

    // Construtor Completo
    public Mesa(Long id, String nome, String mestreNome, LocalDateTime dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.mestreNome = mestreNome;
        this.dataCriacao = dataCriacao;
    }

    // Gatilho executado antes de salvar no banco
    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
    }

    // Getters e Setters Manuais (Imunes a quebras de versão do Java)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMestreNome() {
        return mestreNome;
    }

    public void setMestreNome(String mestreNome) {
        this.mestreNome = mestreNome;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}