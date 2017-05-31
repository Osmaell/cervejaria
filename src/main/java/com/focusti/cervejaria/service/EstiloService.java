package com.focusti.cervejaria.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focusti.cervejaria.model.Estilo;
import com.focusti.cervejaria.repository.Estilos;
import com.focusti.cervejaria.service.exception.NomeJaCadastradoException;

@Service
public class EstiloService {

	@Autowired
	private Estilos estilos;
	
	public void salvar(Estilo estilo) {
		
		Optional<Estilo> estiloOptional = estilos.findByNomeIgnoreCase(estilo.getNome());
		
		if ( estilo != null && estiloOptional.isPresent()) {
			throw new NomeJaCadastradoException("Já existe um estilo com esse nome");
		}
		
		estilos.save(estilo);
	}
	
}