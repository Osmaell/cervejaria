package com.focusti.cervejaria.repository;
	
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.focusti.cervejaria.model.Estilo;
	
@Repository
public interface Estilos extends JpaRepository<Estilo, Long>{
	
	public Optional<Estilo> findByNomeIgnoreCase(String nome);
	
}