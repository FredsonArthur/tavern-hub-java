package com.tavernhub.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_personagem")
// Mágica do Soft Delete: Transforma o "delete" físico em um "update" lógico
@SQLDelete(sql = "UPDATE tb_personagem SET deletado = true WHERE id = ?")
// Garante que todas as consultas (SELECT) ignorem os registros deletados por padrão
@SQLRestriction("deletado = false")
public class Personagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do personagem é obrigatório.")
    @Size(min = 2, max = 100, message = "O nome do personagem deve ter entre 2 e 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "A classe do personagem é obrigatória.")
    @Size(max = 50, message = "A classe deve ter no máximo 50 caracteres.")
    @Column(nullable = false, length = 50)
    private String classe;

    @NotNull(message = "O nível é obrigatório.")
    @Min(value = 1, message = "O nível mínimo permitido é 1.")
    @Max(value = 20, message = "O nível máximo permitido no sistema é 20.")
    @Column(nullable = false)
    private Integer nivel;

    @NotNull(message = "Os pontos de vida são obrigatórios.")
    @Min(value = 0, message = "Os pontos de vida não podem ser negativos.")
    @Column(name = "pontos_vida", nullable = false)
    private Integer pontosVida;

    @Column(nullable = false)
    private boolean deletado = false;

    // Relacionamento Muitos para Um: Vários personagens pertencem a uma Mesa
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    // Construtores
    public Personagem() {
    }

    public Personagem(Long id, String nome, String classe, Integer nivel, Integer pontosVida, Mesa mesa) {
        this.id = id;
        this.nome = nome;
        this.classe = classe;
        this.nivel = nivel;
        this.pontosVida = pontosVida;
        this.mesa = mesa;
    }

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }

    public Integer getNivel() { return nivel; }
    public void setNivel(Integer nivel) { this.nivel = nivel; }

    public Integer getPontosVida() { return pontosVida; }
    public void setPontosVida(Integer pontosVida) { this.pontosVida = pontosVida; }

    public boolean isDeletado() { return deletado; }
    public void setDeletado(boolean deletado) { this.deletado = deletado; }

    public Mesa getMesa() { return mesa; }
    public void setMesa(Mesa mesa) { this.mesa = mesa; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }

    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}