package com.focusti.cervejaria.service;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focusti.cervejaria.model.Cliente;
import com.focusti.cervejaria.repository.Clientes;
	
@Service
public class ClienteService {
	
	@Autowired
	private Clientes clientes;
	
	public void salvar(Cliente cliente) {
		clientes.save(cliente);
	}
	
}