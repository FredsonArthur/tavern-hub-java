package com.tavernhub.dto;

import com.tavernhub.domain.Personagem;
import java.time.LocalDateTime;

public record PersonagemExibicaoDTO(
    Long id,
    String nome,
    String classe,
    Integer nivel,
    Integer pontosVida,
    MesaExibicaoDTO mesa,
    LocalDateTime dataCriacao
) {
    public PersonagemExibicaoDTO(Personagem personagem) {
        this(
            personagem.getId(),
            personagem.getNome(),
            personagem.getClasse(),
            personagem.getNivel(),
            personagem.getPontosVida(),
            personagem.getMesa() != null ? new MesaExibicaoDTO(personagem.getMesa()) : null,
            personagem.getDataCriacao()
        );
    }
}