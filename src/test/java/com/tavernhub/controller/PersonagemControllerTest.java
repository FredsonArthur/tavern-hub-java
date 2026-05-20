package com.tavernhub.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersonagemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deveria retornar status 200 OK e a estrutura de paginação ao listar personagens")
    void deveriaListarPersonagensComPaginacao() throws Exception {
        mockMvc.perform(get("/api/personagens")
                        .param("page", "0")
                        .param("size", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray()) // Garante que a raiz traz a estrutura paginada do Spring
                .andExpect(jsonPath("$.pageable").exists());
    }

    @Test
    @DisplayName("Deveria retornar BadRequest (400) ao tentar criar um personagem com nível maior que 20 ou vida negativa")
    void deveriaBarrarCriacaoComDadosInvalidos() throws Exception {
        // Payload violando as restrições de @Min, @Max e @NotBlank da Fase 4
        String jsonInvalido = """
                {
                    "nome": "",
                    "classe": "Mago",
                    "nivel": 25,
                    "pontosVida": -10
                }
                """;

        mockMvc.perform(post("/api/personagens/mesa/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest()); // Garante que o GlobalExceptionHandler interceptou o erro 400
    }

    @Test
    @DisplayName("Deveria retornar NotFound (404) ao tentar buscar ou criar personagens em uma mesa inexistente")
    void deveriaRetornarNotFoundParaMesaInexistente() throws Exception {
        mockMvc.perform(get("/api/personagens/mesa/999999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Garante o mapeamento do ObjetoNaoEncontradoException
    }
}