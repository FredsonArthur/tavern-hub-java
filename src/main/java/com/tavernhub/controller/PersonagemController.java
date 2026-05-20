package com.tavernhub.controller;

import com.tavernhub.domain.Mesa;
import com.tavernhub.domain.Personagem;
import com.tavernhub.dto.PersonagemExibicaoDTO; // Importação do novo DTO protegido
import com.tavernhub.exception.ObjetoNaoEncontradoException;
import com.tavernhub.repository.MesaRepository;
import com.tavernhub.repository.PersonagemRepository;
import jakarta.validation.Valid; // Ativador das validações da Fase 4
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personagens")
public class PersonagemController {

    private final PersonagemRepository personajeRepository; // Mantido o nome padronizado do atributo do construtor original
    private final MesaRepository mesaRepository;

    public PersonagemController(PersonagemRepository personajeRepository, MesaRepository mesaRepository) {
        this.personajeRepository = personajeRepository;
        this.mesaRepository = mesaRepository;
    }

    // Listar todos os personagens ativos com paginação, ordenação padrão por nome e conversão para DTO seguro
    @GetMapping
    public Page<PersonagemExibicaoDTO> listarTodos(@PageableDefault(size = 10, sort = "nome") Pageable paginacao) {
        return personajeRepository.findAll(paginacao)
                .map(PersonagemExibicaoDTO::new);
    }

    // Listar os personagens ativos de uma mesa específica com paginação e conversão para DTO
    @GetMapping("/mesa/{mesaId}")
    public Page<PersonagemExibicaoDTO> listarPorMesa(@PathVariable Long mesaId, @PageableDefault(size = 10, sort = "nome") Pageable paginacao) {
        if (!mesaRepository.existsById(mesaId)) {
            throw new ObjetoNaoEncontradoException("Mesa não encontrada com o ID: " + mesaId);
        }
        return personajeRepository.findByMesaId(mesaId, paginacao)
                .map(PersonagemExibicaoDTO::new);
    }

    // Criar um personagem validado vinculado a uma mesa existente
    @PostMapping("/mesa/{mesaId}")
    public ResponseEntity<PersonagemExibicaoDTO> criar(@PathVariable Long mesaId, @Valid @RequestBody Personagem personaje) {
        Mesa mesa = mesaRepository.findById(mesaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Mesa não encontrada com o ID: " + mesaId));

        personaje.setMesa(mesa);
        
        Personagem novoPersonagem = personajeRepository.save(personaje);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PersonagemExibicaoDTO(novoPersonagem));
    }

    // Soft Delete mantido com segurança integrada
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!personajeRepository.existsById(id)) {
            throw new ObjetoNaoEncontradoException("Personagem não encontrado com o ID: " + id);
        }
        personajeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}