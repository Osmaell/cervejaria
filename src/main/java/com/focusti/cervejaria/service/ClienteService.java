package com.focusti.cervejaria.service;
	
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focusti.cervejaria.model.Cliente;
import com.focusti.cervejaria.repository.Clientes;
import com.focusti.cervejaria.service.exception.CpfCnpjJaCadastrado;
	
@Service
public class ClienteService {
	
	@Autowired
	private Clientes clientes;
	
	public void salvar(Cliente cliente) {
		
		Optional<Cliente> clienteExistente = clientes.findByCpfOuCnpj(cliente.getCpfOuCnpjSemFormatacao());
		
		if (clienteExistente.isPresent()) {
			throw new CpfCnpjJaCadastrado("CPF/CNPJ já cadastrado");
		}
		
		clientes.save(cliente);
	}
	
	public List<Cliente> buscar() {
		return clientes.listar();
	}
	
}