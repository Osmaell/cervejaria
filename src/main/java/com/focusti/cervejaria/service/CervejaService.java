package com.focusti.cervejaria.service;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focusti.cervejaria.model.Cerveja;
import com.focusti.cervejaria.repository.Cervejas;
	
@Service
public class CervejaService {
	
	@Autowired
	private Cervejas cervejas;
	
	public void salvar(Cerveja cerveja) {
		cervejas.save(cerveja);
	}
	
}