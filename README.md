# 🏰 TavernHub API — O Lobby de Sessão Interativo para RPG

[![Java Version](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot Version](https://img.shields.io/badge/Spring_Boot-3.4.2-brightgreen?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Database](https://img.shields.io/badge/Database-H2_Memory-blue?style=for-the-badge&logo=databricks&logoColor=white)](https://www.h2database.com/)
[![OS](https://img.shields.io/badge/OS-Ubuntu_Linux-E95420?style=for-the-badge&logo=ubuntu&logoColor=white)](https://ubuntu.com/)

O **TavernHub** é uma aplicação projetada para ser o ponto de encontro de mesas de RPG. O objetivo é oferecer uma API REST de alta performance para gerenciar um dashboard em tempo real onde jogadores e mestres sincronizam rolagens de dados e gerenciam seus personagens, trazendo toda a robustez e segurança do ecossistema **Spring Boot 3** utilizando as novidades do **Java 21**.

O sistema gerencia mesas de jogo e os seus respectivos personagens através de regras de negócio complexas, como exclusão lógica (*Soft Delete*), consistência de fuso horário internacional e relacionamentos automatizados via JPA/Hibernate.

---

## 🛠️ Tecnologias e Configurações de Ambiente

| Componente | Tecnologia Empregada | Detalhes de Implementação |
| :--- | :--- | :--- |
| **Linguagem** | Java 21 (LTS) | Compilação compatível (Bytecode 65), uso de records e melhorias de concorrência. |
| **Framework Core** | Spring Boot 3.4.2 | Arquitetura REST robusta, inversão de controle e injeção de dependências nativa. |
| **Persistência** | Spring Data JPA / Hibernate 6.6 | Abstração completa de queries e mapeamento ORM automatizado. |
| **Gestão de Dados**| H2 Database | Banco de dados relacional em memória para desenvolvimento ágil e testes rápidos. |
| **Timezone** | `America/Sao_Paulo` | Log de atividades e carimbos de data/hora sincronizados com o Horário de Brasília (UTC-3). |
| **Ambiente** | Ubuntu Linux / IntelliJ IDEA | Desenvolvimento nativo em terminal Unix e gerenciamento via Maven Wrapper. |

---

## 🏗️ Arquitetura do Sistema e Estrutura de Pastas

A API segue o padrão rígido de três camadas de responsabilidade clássicas do ecossistema Spring Boot, isolando completamente as regras de banco, negócios e transporte:

```text
src/main/java/com/tavernhub/
├── domain/          # Modelos de dados mapeados no ORM (Entidades como Personagem, Mesa)
├── repository/      # Interfaces de acesso ao banco (MesaRepository, PersonagemRepository)
├── exception/       # Classes de tratamento de erros, record de resposta e exceções customizadas
├── controller/      # Controladores REST que expõem e gerenciam as rotas HTTP da API
└── TavernHubApplication.java # Classe de inicialização e bootstrap do ecossistema Spring
```
## 🎯 Funcionalidades & Progresso do Projeto

### 🎲 Fase 1: O Tabuleiro Estático & Persistência `(Concluída ✔️)`
- [x] **Setup Inicial:** Configuração do ecossistema do projeto com Spring Boot Starter Web e JPA.
- [x] **Compatibilidade Java 21:** Configuração do compilador Maven (`pom.xml`) alinhado com o bytecode 65.
- [x] **Comunicação Base:** Integração assíncrona estabelecida através do retorno nativo de objetos serializados automaticamente in JSON pela Jackson API.
- [x] **Log de Atividades:** Registro de tabelas automáticas no console do Hibernate contendo tratamento completo de fuso horário e **Internacionalização para o Horário de Brasília (UTC-3)** via gatilhos `@PrePersist`.

### 🛡️ Fase 2: O Coração do RPG - CRUD & Entidades `(Concluída ✔️)`
- [x] **Gestão de Mesas:** Mapeamento ORM e rotas completas para criação (`POST`) e listagem geral (`GET`) de mesas de jogo ativas.
- [x] **Gestão de Personagens:** Modelo operacional contendo rotas desacopladas para criação com amarrações chave-estrangeira diretas.
- [x] **Integração Relacional:** Arquitetura de banco configurada via relacionamento `@ManyToOne` (Muitos Personagens pertencem a uma Mesa) garantindo consistência referencial total.

### 🔄 Fase 3: Arquitetura Avançada & Exclusão Lógica `(Concluída ✔️)`
- [x] **Soft Delete de Personagens:** Mecanismo de exclusão lógica robusto implementado via anotação `@SQLDelete`, impedindo a remoção física e mantendo o histórico de auditoria do banco.
- [x] **Cláusulas de Restrição Dinâmicas:** Injeção automática da condicional `@SQLRestriction("deletado = false")` em tempo de execução, limpando todas as consultas (`SELECT`) da aplicação de maneira transparente.

### 🚨 Fase 3.5: Tratamento de Exceções Global `(Concluída ✔️)`
- [x] **Interceptador de Erros:** Criação do `GlobalExceptionHandler` utilizando a anotação `@RestControllerAdvice` para capturar falhas em tempo de execução.
- [x] **Padronização de Respostas:** Implementação de estruturas de erro imutáveis através de Java Records (`ErroResposta`).
- [x] **Exceções Customizadas:** Desenvolvimento da classe de negócio `ObjetoNaoEncontradoException` mapeada especificamente para interceptar e retornar o status HTTP 404 (Not Found) de forma limpa.