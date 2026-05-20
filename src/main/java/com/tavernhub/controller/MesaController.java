package com.tavernhub.controller;

import com.tavernhub.domain.Mesa;
import com.tavernhub.repository.MesaRepository;
import jakarta.validation.Valid; // Importação crucial para ativar as validações
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@CrossOrigin("*") // Libera o acesso CORS para o seu frontend JavaScript separado conectar com segurança
public class MesaController {

    private final MesaRepository mesaRepository;

    // Injeção de dependência via construtor
    public MesaController(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    // Rota GET: Retorna todas as mesas do banco
    @GetMapping
    public List<Mesa> listarTodas() {
        return mesaRepository.findAll();
    }

    // Rota POST: Cria uma nova mesa na taverna aplicando as validações da Fase 4
    @PostMapping
    public ResponseEntity<Mesa> criar(@Valid @RequestBody Mesa mesa) { // @Valid adicionado aqui
        Mesa novaMesa = mesaRepository.save(mesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMesa);
    }
}