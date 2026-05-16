package com.tavernhub.controller;

import com.tavernhub.domain.Mesa;
import com.tavernhub.repository.MesaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {

    private final MesaRepository mesaRepository;

    // Injeção de dependência via construtor (o Spring cuida disso sozinho)
    public MesaController(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    // Rota GET: Retorna todas as mesas do banco
    @GetMapping
    public List<Mesa> listarTodas() {
        return mesaRepository.findAll();
    }

    // Rota POST: Cria uma nova mesa na taverna
    @PostMapping
    public ResponseEntity<Mesa> criar(@RequestBody Mesa mesa) {
        Mesa novaMesa = mesaRepository.save(mesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMesa);
    }
}