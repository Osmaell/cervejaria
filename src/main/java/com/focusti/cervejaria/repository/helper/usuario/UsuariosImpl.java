package com.focusti.cervejaria.repository.helper.usuario;
	
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.focusti.cervejaria.model.Usuario;
	
public class UsuariosImpl implements UsuariosQueries {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Optional<Usuario> porEmailEAtivo(String email) {
		
		Optional<Usuario> usuario = entityManager
				.createQuery("from Usuario where lower(email) = lower(:email) and ativo = true", Usuario.class)
				.setParameter("email", email).getResultList().stream().findFirst();
		
		return usuario;
	}
	
}