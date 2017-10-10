package com.focusti.cervejaria.security;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.focusti.cervejaria.model.Usuario;
import com.focusti.cervejaria.repository.Usuarios;

@Service
public class AppUserDetailsService implements UserDetailsService {
	
	@Autowired
	private Usuarios usuarios;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<Usuario> optional = usuarios.porEmailEAtivo(email);
		
		Usuario usuario = optional.orElseThrow( () -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
		
		return new User(usuario.getEmail(), usuario.getSenha(), new HashSet<>());
	}
	
}