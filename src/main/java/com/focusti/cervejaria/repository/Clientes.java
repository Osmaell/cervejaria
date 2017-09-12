package com.focusti.cervejaria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.focusti.cervejaria.model.Cliente;

@Repository
public interface Clientes extends JpaRepository<Cliente, Long> {

	public Optional<Cliente> findByCpfOuCnpj(String cpfOuCnpj);
	
}