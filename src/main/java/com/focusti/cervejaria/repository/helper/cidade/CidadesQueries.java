package com.focusti.cervejaria.repository.helper.cidade;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.focusti.cervejaria.model.Cidade;
import com.focusti.cervejaria.repository.filter.CidadeFilter;

public interface CidadesQueries {
	
	public List<Cidade> buscar();
	
	public Page<Cidade> filtrar(Pageable pageable, CidadeFilter filter);
	
}