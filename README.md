# 🏰 TavernHub API — O Lobby de Sessão Interativo para RPG

[![Java Version](https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot Version](https://img.shields.io/badge/Spring_Boot-3.4.2-brightgreen?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Database](https://img.shields.io/badge/Database-H2_Memory-blue?style=for-the-badge&logo=databricks&logoColor=white)](https://www.h2database.com/)
[![OS](https://img.shields.io/badge/OS-Ubuntu_Linux-E95420?style=for-the-badge&logo=ubuntu&logoColor=white)](https://ubuntu.com/)

O **TavernHub** é uma aplicação projetada para ser o ponto de encontro de mesas de RPG. O objetivo é oferecer uma API REST de alta performance para gerenciar um dashboard em tempo real onde jogadores e mestres sincronizam dados e gerenciam seus personagens, trazendo toda a robustez e segurança do ecossistema **Spring Boot 3** utilizando o que há de mais moderno no **Java 25**.

O sistema gerencia mesas de jogo e os seus respectivos personagens através de regras de negócio complexas, como exclusão lógica (*Soft Delete*), consistência de fuso horário internacional e relacionamentos automatizados via JPA/Hibernate isolados por DTOs imutáveis.

---

## 🛠️ Tecnologias e Configurações de Ambiente

| Componente | Tecnologia Empregada | Detalhes de Implementação |
| :--- | :--- | :--- |
| **Linguagem** | Java 25 (LTS) | Uso de records, novos métodos de coleções e melhorias de concorrência. |
| **Framework Core** | Spring Boot 3.4.2 | Arquitetura REST robusta, inversão de controle e injeção de dependências nativa. |
| **Persistência** | Spring Data JPA / Hibernate 6.6 | Abstração completa de queries e mapeamento ORM automatizado. |
| **Gestão de Dados**| H2 Database | Banco de dados relacional em memória para desenvolvimento ágil e testes rápidos. |
| **Timezone** | `America/Sao_Paulo` | Log de atividades, carimbos de auditoria e respostas de erro sincronizados com o Horário de Brasília (UTC-3). |
| **Ambiente** | Ubuntu Linux / VS Code | Desenvolvimento nativo em terminal Unix e gerenciamento via Maven Wrapper. |

---

## 🏗️ Arquitetura do Sistema e Estrutura de Pastas

A API segue o padrão rígido de camadas de responsabilidade do ecossistema Spring Boot, isolando completamente as regras de banco, negócios e transporte:

```text
src/main/java/com/tavernhub/
├── domain/          # Modelos de dados mapeados no ORM (Entidades como Personagem, Mesa)
├── repository/      # Interfaces de acesso ao banco (MesaRepository, PersonagemRepository)
├── dto/             # Java Records para transferência segura de dados (Data Transfer Objects)
├── exception/       # Classes de tratamento de erros, record de resposta e exceções customizadas
├── controller/      # Controladores REST que expõem e gerenciam as rotas HTTP da API
└── TavernHubApplication.java # Classe de inicialização e bootstrap do ecossistema Spring
```
---

### 🎯 Bloco 2: Funcionalidades & Progresso do Projeto

Este bloco contém o mapeamento de todas as fases, incluindo a **Fase 5 totalmente concluída e checada** após o sucesso dos testes e da paginação.

```markdown
## 🎯 Funcionalidades & Progresso do Projeto

### 🎲 Fase 1: O Tabuleiro Estático & Persistência `(Concluída ✔️)`
- [x] **Setup Inicial:** Configuração do ecossistema do projeto com Spring Boot Starter Web e JPA.
- [x] **Compatibilidade Avançada:** Configuração do compilador Maven alinhado com o ambiente Java atual.
- [x] **Comunicação Base:** Integração estabelecida através do retorno nativo de objetos serializados em JSON pela Jackson API.
- [x] **Log de Atividades:** Registro de tabelas automáticas no console do Hibernate contendo tratamento completo de fuso horário regional via gatilhos de auditoria do JPA.

### 🛡️ Fase 2: O Coração do RPG - CRUD & Entidades `(Concluída ✔️)`
- [x] **Gestão de Mesas:** Mapeamento ORM e rotas completas para criação (`POST`) e listagem geral (`GET`) de mesas de jogo ativas.
- [x] **Gestão de Personagens:** Modelo operacional contendo rotas desacopladas para criação com amarrações chave-estrangeira diretas.
- [x] **Integração Relacional:** Arquitetura de banco configurada via relacionamento `@ManyToOne` (Muitos Personagens pertencem a uma Mesa).
- [x] **Proteção Circular (DTOs):** Implementação de Records de exibição (`PersonagemExibicaoDTO`) eliminando dependências cíclicas e o erro `LazyInitializationException` nas rotas.

### 🔄 Fase 3: Arquitetura Avançada & Exclusão Lógica `(Concluída ✔️)`
- [x] **Soft Delete de Personagens:** Mecanismo de exclusão lógica robusto implementado via anotação `@SQLDelete`, mudando o estado do campo `deletado` sem apagar os dados físicos.
- [x] **Cláusulas de Restrição Dinâmicas:** Injeção automática da condicional `@SQLRestriction("deletado = false")` em tempo de execução, filtrando todas as consultas de maneira transparente.

### 🚨 Fase 3.5: Tratamento de Exceções Global `(Concluída ✔️)`
- [x] **Interceptador de Erros:** Criação do `GlobalExceptionHandler` utilizando a anotação `@RestControllerAdvice` para capturar falhas globais.
- [x] **Fuso Horário nos Erros:** Garantia de sincronização regional (`America/Sao_Paulo`) nos carimbos de tempo de todas as falhas capturadas.
- [x] **Exceções Customizadas:** Desenvolvimento da classe de negócio `ObjetoNaoEncontradoException` mapeada especificamente para retornar o status HTTP 404 (Not Found).

### 🛡️ Fase 4: Validação de Dados com Bean Validation `(Concluída ✔️)`
- [x] **Regras de Negócio nas Entidades:** Restrições rígidas aplicadas em `Mesa` e `Personagem` utilizando `@NotBlank`, `@Size`, `@Min` e `@Max`.
- [x] **Interceptação de Dados Inválidos:** Ativação do validador nas rotas através do parâmetro `@Valid` no corpo das requisições HTTP (`@RequestBody`).
- [x] **Exposição de Erros Limpa:** Interceptação das falhas de validação mecânica e de restrição do banco de dados, devolvendo o status 400 Bad Request com strings detalhadas sobre cada campo incorreto.

### 🚀 Fase 5: Maturidade & Produção `(Concluída ✔️)`
- [x] **Documentação Automatizada:** Integração completa com Springdoc OpenAPI / Swagger UI para testes visuais das rotas e mapeamento interativo de payloads direto pelo navegador.
- [x] **Performance de Dados:** Implementação de paginação (`Pageable`) e ordenação dinâmica com mapeamento seguro de DTOs nas listagem de personagens para otimização de memória do servidor.
- [x] **Suite de Testes de Integração:** Desenvolvimento de testes automatizados usando JUnit 5 e MockMvc, blindando as controllers contra payloads inválidos e garantindo estabilidade e o status HTTP correto das regras de negócio.