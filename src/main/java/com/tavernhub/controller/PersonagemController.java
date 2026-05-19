package com.tavernhub.controller;

import com.tavernhub.domain.Mesa;
import com.tavernhub.domain.Personagem;
import com.tavernhub.dto.PersonagemExibicaoDTO; // Importação do novo DTO protegido
import com.tavernhub.exception.ObjetoNaoEncontradoException;
import com.tavernhub.repository.MesaRepository;
import com.tavernhub.repository.PersonagemRepository;
import jakarta.validation.Valid; // Ativador das validações da Fase 4
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personagens")
public class PersonagemController {

    private final PersonagemRepository personagemRepository; // Nome limpo e padronizado
    private final MesaRepository mesaRepository;

    // Construtor corrigido de 'personajeRepository' para 'personagemRepository'
    public PersonagemController(PersonagemRepository personagemRepository, MesaRepository mesaRepository) {
        this.personagemRepository = personagemRepository;
        this.mesaRepository = mesaRepository;
    }

    // Listar todos os personagens ativos convertidos para DTO seguro
    @GetMapping
    public List<PersonagemExibicaoDTO> listarTodos() {
        return personagemRepository.findAll()
                .stream()
                .map(PersonagemExibicaoDTO::new)
                .toList();
    }

    // Listar os personagens ativos de uma mesa específica convertidos para DTO
    @GetMapping("/mesa/{mesaId}")
    public List<PersonagemExibicaoDTO> listarPorMesa(@PathVariable Long mesaId) {
        if (!mesaRepository.existsById(mesaId)) {
            throw new ObjetoNaoEncontradoException("Mesa não encontrada com o ID: " + mesaId);
        }
        return personagemRepository.findByMesaId(mesaId)
                .stream()
                .map(PersonagemExibicaoDTO::new)
                .toList();
    }

    // Criar um personagem validado vinculado a uma mesa existente
    @PostMapping("/mesa/{mesaId}")
    public ResponseEntity<PersonagemExibicaoDTO> criar(@PathVariable Long mesaId, @Valid @RequestBody Personagem personaje) { // @Valid adicionado
        Mesa mesa = mesaRepository.findById(mesaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Mesa não encontrada com o ID: " + mesaId));

        personaje.setMesa(mesa);
        
        // Persistência direta e limpa sem o método privado redundante
        Personagem novoPersonagem = personagemRepository.save(personaje);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PersonagemExibicaoDTO(novoPersonagem));
    }

    // Soft Delete mantido com segurança integrada
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!personagemRepository.existsById(id)) {
            throw new ObjetoNaoEncontradoException("Personagem não encontrado com o ID: " + id);
        }
        personagemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}