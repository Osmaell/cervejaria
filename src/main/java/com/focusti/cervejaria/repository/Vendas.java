package com.focusti.cervejaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.focusti.cervejaria.model.Venda;
import com.focusti.cervejaria.repository.helper.venda.VendasQueries;

@Repository
public interface Vendas extends JpaRepository<Venda, Long>, VendasQueries {
	
}