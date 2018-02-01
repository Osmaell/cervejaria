package com.focusti.cervejaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.focusti.cervejaria.model.Usuario;
import com.focusti.cervejaria.repository.Usuarios;
import com.focusti.cervejaria.service.exception.EmailUsuarioJaCadastradoException;
import com.focusti.cervejaria.service.exception.SenhaObrigatoriaUsuarioException;
import com.google.common.base.Optional;

@Service
public class UsuarioService {
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void salvar(Usuario usuario) {
		
		Optional<Usuario> usuarioExistente = usuarios.findByEmail(usuario.getEmail());
		
		if (usuarioExistente.isPresent() && usuario.isNovo()) {
			throw new EmailUsuarioJaCadastradoException("E-mail já cadastrado");
		}
		
		if(usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatória para novo usuário");
		}
		
		if (usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha()) ) {
			usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
		} else if ( StringUtils.isEmpty(usuario.getSenha()) ) {
			usuario.setSenha( usuarioExistente.get().getSenha() );
		}
		
		usuario.setConfirmacaoSenha(usuario.getSenha());
		
		if (!usuario.isNovo() && usuario.getAtivo() == null) {
			usuario.setAtivo(usuarioExistente.get().getAtivo());
		}
		
		usuarios.save(usuario);
	}
	
	public void alterarStatus(Long[] codigos, StatusUsuario status) {
		status.executar(codigos, usuarios);
	}
	
}