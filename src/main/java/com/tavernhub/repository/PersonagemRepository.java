package com.tavernhub.repository;

import com.tavernhub.domain.Personagem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonagemRepository extends JpaRepository<Personagem, Long> {

    // Método customizado atualizado: Busca os personagens de uma mesa específica aplicando paginação e ordenação
    Page<Personagem> findByMesaId(Long mesaId, Pageable pageable);
}