package com.tavernhub.repository;

import com.tavernhub.domain.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    // O JpaRepository injeta automaticamente métodos como save(), findById(), findAll() e deleteById()
}