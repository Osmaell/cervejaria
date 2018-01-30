package com.focusti.cervejaria.service;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.focusti.cervejaria.model.Cerveja;
import com.focusti.cervejaria.repository.Cervejas;
import com.focusti.cervejaria.service.exception.ImpossivelExcluirEntidadeException;
	
@Service
public class CervejaService {
	
	@Autowired
	private Cervejas cervejas;
	
	public void salvar(Cerveja cerveja) {
		cervejas.save(cerveja);
	}
	
	public void excluir(Long codigo) {
		
		try {
			cervejas.delete(codigo);
			cervejas.flush();
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar cerveja. Já foi usada em alguma venda.");
		}
		
	}
	
}