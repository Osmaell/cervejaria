package com.focusti.cervejaria.repository.helper.venda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.focusti.cervejaria.model.Venda;
import com.focusti.cervejaria.repository.filter.VendaFilter;

public interface VendasQueries {
	
	public Page<Venda> filtrar(VendaFilter filtro, Pageable pageable);
	
}