package com.tavernhub.repository;

import com.tavernhub.domain.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PersonagemRepository extends JpaRepository<Personagem, Long> {

    // Método customizado: Busca todos os personagens de uma mesa específica
    List<Personagem> findByMesaId(Long mesaId);
}