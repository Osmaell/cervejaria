package com.focusti.cervejaria.repository.helper.estilo;
	
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.focusti.cervejaria.model.Estilo;
import com.focusti.cervejaria.repository.filter.EstiloFilter;
	
public interface EstilosQueries {
	
	public List<Estilo> listar();
	
	public Page<Estilo> filtrar(EstiloFilter filter, Pageable pageable);
	
}