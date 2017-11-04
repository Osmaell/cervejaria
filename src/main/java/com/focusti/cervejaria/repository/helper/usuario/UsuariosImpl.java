package com.focusti.cervejaria.repository.helper.usuario;
	
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.util.StringUtils;

import com.focusti.cervejaria.model.Grupo;
import com.focusti.cervejaria.model.Usuario;
import com.focusti.cervejaria.model.UsuarioGrupo;
import com.focusti.cervejaria.repository.filter.UsuarioFilter;
	
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

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> filtrar(UsuarioFilter filtro) {
		
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Usuario.class);
		
		// criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		adicionarFiltro(filtro, criteria);
		
		return criteria.list();
	}
	
	private void adicionarFiltro(UsuarioFilter filtro, Criteria criteria) {
		
		if (filtro != null) {
			
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(filtro.getEmail())) {
				criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.START));
			}
			
			// criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
			
			if (filtro.getGrupos() != null && !filtro.getGrupos().isEmpty()) {
				
				List<Criterion> subQueries = new ArrayList<>();
				
				for(Long codigoGrupo : filtro.getGrupos().stream().mapToLong(Grupo::getCodigo).toArray()) {
					
					// criteria destacada
					DetachedCriteria dc = DetachedCriteria.forClass(UsuarioGrupo.class);
					dc.add(Restrictions.eq("id.grupo.codigo", codigoGrupo));
					dc.setProjection(Projections.property("id.usuario"));
					
					subQueries.add(Subqueries.propertyIn("codigo", dc));
					
				}
				
				Criterion[] criterions = new Criterion[subQueries.size()];
				criteria.add(Restrictions.and(subQueries.toArray(criterions)));
				
			}
			
		}
		
	}
	
}