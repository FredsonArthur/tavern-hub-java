package com.tavernhub.dto;

import com.tavernhub.domain.Mesa;
import java.time.LocalDateTime;

public record MesaExibicaoDTO(
    Long id, 
    String nome, 
    String mestreNome, 
    LocalDateTime dataCriacao
) {
    // Construtor compacto que transforma a entidade JPA no nosso DTO imutável
    public MesaExibicaoDTO(Mesa mesa) {
        this(mesa.getId(), mesa.getNome(), mesa.getMestreNome(), mesa.getDataCriacao());
    }
}