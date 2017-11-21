package com.focusti.cervejaria.repository.helper.cerveja;
	
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.focusti.cervejaria.dto.CervejaDTO;
import com.focusti.cervejaria.model.Cerveja;
import com.focusti.cervejaria.repository.filter.CervejaFilter;
	
public interface CervejasQueries {
	
	public List<Cerveja> listar();
	
	public Page<Cerveja> filtar(CervejaFilter filter, Pageable pageable);
	
	public List<CervejaDTO> porSkuOuNome(String skuOuNome);
	
}