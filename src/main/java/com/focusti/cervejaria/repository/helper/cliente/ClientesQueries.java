package com.focusti.cervejaria.repository.helper.cliente;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.focusti.cervejaria.model.Cliente;
import com.focusti.cervejaria.repository.filter.ClienteFilter;

public interface ClientesQueries {
	
	public List<Cliente> listar();
	
	public Page<Cliente> filtrar(Pageable pageable, ClienteFilter clienteFilter);
	
}