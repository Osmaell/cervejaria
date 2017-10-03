package com.focusti.cervejaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focusti.cervejaria.model.Usuario;
import com.focusti.cervejaria.repository.Usuarios;
import com.focusti.cervejaria.service.exception.UsuarioJaCadastradoException;
import com.google.common.base.Optional;

@Service
public class UsuarioService {
	
	@Autowired
	private Usuarios usuarios;
	
	public void salvar(Usuario usuario) {
		
		Optional<Usuario> optional = usuarios.findByNomeAndEmail(usuario.getNome(), usuario.getEmail());
		
		if (usuario.getCodigo() == null && optional.isPresent()) {
			throw new UsuarioJaCadastradoException("Já existe um usuário cadastrado");
		}
		
		usuarios.save(usuario);
	}
	
}