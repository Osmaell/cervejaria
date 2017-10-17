package com.focusti.cervejaria.repository.helper.usuario;
	
import java.util.List;
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

	@Override
	public List<String> permissoes(Usuario usuario) {

		String jpql = "select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario";
		
		List<String> permissoes = entityManager.createQuery(jpql, String.class)
									.setParameter("usuario", usuario).getResultList();
		
		return permissoes;
	}
	
}