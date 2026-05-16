# 🏰 TavernHub API — Documentação Completa do Projeto

[![Java Version](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.2-brightgreen?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![Database](https://img.shields.io/badge/Database-H2__Memory-blue?style=for-the-badge&logo=databricks)](https://www.h2database.com/)

O **TavernHub** é um Lobby de Sessão Interativo para RPG, desenvolvido como uma API REST robusta utilizando **Spring Boot 3** e **Java 21**. Este projeto consolida a migração e modernização de um ecossistema anteriormente estruturado em Python/Django para a arquitetura de alta performance e tipagem estática do ecossistema Java profissional.

O sistema gerencia mesas de jogo e os seus respectivos personagens, aplicando regras de negócio customizadas como exclusão lógica (*Soft Delete*), consistência de fuso horário e relacionamentos referenciais automatizados via ORM (Hibernate).

---

## 🛠️ Tecnologias e Configurações de Ambiente

- **Linguagem:** Java 21 (LTS) — Dragonwell JDK (Alibaba)
- **Framework Core:** Spring Boot 3.4.2
- **Persistência de Dados:** Spring Data JPA / Hibernate ORM 6.6
- **Banco de Dados:** H2 Database (Engine SQL em memória para desenvolvimento ágil)
- **Timezone:** Configurado explicitamente para `America/Sao_Paulo` (Horário de Brasília, UTC-3)
- **Ambiente de Desenvolvimento:** Ubuntu Linux / IntelliJ IDEA

---

## 🏗️ Arquitetura do Sistema e Estrutura de Pastas

A API segue o padrão rígido de camadas de responsabilidade do Spring Boot. Toda a lógica está contida sob o pacote principal `com.tavernhub`:

```text
src/main/java/com/tavernhub/
├── domain/          # Modelos de dados e mapeamento ORM (Entidades)
├── repository/      # Interfaces de abstração e acesso ao banco (JPA)
├── controller/      # Controladores REST que expõem as rotas HTTP
└── TavernHubApplication.java # Classe de inicialização do Spring Boot