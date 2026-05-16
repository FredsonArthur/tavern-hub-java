package com.tavernhub.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

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

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 50)
    private String classe;

    @Column(nullable = false)
    private Integer nivel;

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
}