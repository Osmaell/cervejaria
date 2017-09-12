package com.focusti.cervejaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.focusti.cervejaria.model.Cliente;

@Repository
public interface Clientes extends JpaRepository<Cliente, Long> {
	
}