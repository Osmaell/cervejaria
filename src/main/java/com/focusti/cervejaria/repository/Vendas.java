package com.focusti.cervejaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.focusti.cervejaria.model.Venda;

@Repository
public interface Vendas extends JpaRepository<Venda, Long> {
	
}