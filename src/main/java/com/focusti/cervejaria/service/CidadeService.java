package com.focusti.cervejaria.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focusti.cervejaria.model.Cidade;
import com.focusti.cervejaria.repository.Cidades;
import com.focusti.cervejaria.service.exception.CidadeJaCadastradaException;

@Service
public class CidadeService {
	
	@Autowired
	private Cidades cidades;
	
	public void salvar(Cidade cidade) {
		
		Optional<Cidade> optional = cidades.findByNomeAndEstado(cidade.getNome(), cidade.getEstado());
		
		if (cidade.getCodigo() == null && optional.isPresent()) {
			throw new CidadeJaCadastradaException("Cidade já cadastrada");
		}
		
		cidades.save(cidade);
		
	}
	
}