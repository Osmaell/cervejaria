package com.focusti.cervejaria.repository.helper.cerveja;
	
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.focusti.cervejaria.model.Cerveja;
import com.focusti.cervejaria.repository.filter.CervejaFilter;
	
public interface CervejasQueries {
	
	public List<Cerveja> listar();
	
	public List<Cerveja> filtar(CervejaFilter filter, Pageable pageable);
	
}