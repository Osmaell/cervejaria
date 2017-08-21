package com.focusti.cervejaria.repository;
	
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.focusti.cervejaria.model.Estilo;
import com.focusti.cervejaria.repository.helper.estilo.EstilosQueries;
	
@Repository
public interface Estilos extends JpaRepository<Estilo, Long>, EstilosQueries {
	
	public Optional<Estilo> findByNomeIgnoreCase(String nome);
	
}