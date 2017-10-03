package com.focusti.cervejaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.focusti.cervejaria.model.Usuario;
import com.google.common.base.Optional;

@Repository
public interface Usuarios extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByNomeAndEmail(String nome, String email);
	
}