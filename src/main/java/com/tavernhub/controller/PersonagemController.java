package com.tavernhub.controller;

import com.tavernhub.domain.Mesa;
import com.tavernhub.domain.Personagem;
import com.tavernhub.exception.ObjetoNaoEncontradoException;
import com.tavernhub.repository.MesaRepository;
import com.tavernhub.repository.PersonagemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personagens")
public class PersonagemController {

    private final PersonagemRepository personagemRepository;
    private final MesaRepository mesaRepository;

    public PersonagemController(PersonagemRepository personajeRepository, MesaRepository mesaRepository) {
        this.personagemRepository = personajeRepository;
        this.mesaRepository = mesaRepository;
    }

    // Listar todos os personagens ativos (que não sofreram soft delete)
    @GetMapping
    public List<Personagem> listarTodos() {
        return personagemRepository.findAll();
    }

    // Listar os personagens ativos de uma mesa específica
    @GetMapping("/mesa/{mesaId}")
    public List<Personagem> listarPorMesa(@PathVariable Long mesaId) {
        if (!mesaRepository.existsById(mesaId)) {
            throw new ObjetoNaoEncontradoException("Mesa não encontrada com o ID: " + mesaId);
        }
        return personagemRepository.findByMesaId(mesaId);
    }

    // Criar um personagem vinculado a uma mesa existente
    @PostMapping("/mesa/{mesaId}")
    public ResponseEntity<Personagem> criar(@PathVariable Long mesaId, @RequestBody Personagem personagem) {
        // Se a mesa não existir, dispara a nossa exceção tratada (HTTP 404)
        Mesa mesa = mesaRepository.findById(mesaId)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Mesa não encontrada com o ID: " + mesaId));

        personagem.setMesa(mesa);
        Personagem novoPersonagem = FindAllAndSave(personagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPersonagem);
    }

    private Personagem FindAllAndSave(Personagem personagem) {
        return personagemRepository.save(personagem);
    }

    // Soft Delete: Executa o UPDATE lógico por baixo dos panos via Hibernate
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!personagemRepository.existsById(id)) {
            throw new ObjetoNaoEncontradoException("Personagem não encontrado com o ID: " + id);
        }
        personagemRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna HTTP 204 (Sucesso, sem conteúdo)
    }
}