package com.focusti.cervejaria.repository.helper.estilo;
	
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.focusti.cervejaria.model.Estilo;
import com.focusti.cervejaria.repository.filter.EstiloFilter;
import com.focusti.cervejaria.repository.paginacao.PaginacaoUtil;
	
public class EstilosImpl implements EstilosQueries {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Estilo> listar() {
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Estilo.class);
		return criteria.list();
	}
	
	@SuppressWarnings({ "unchecked" })
	@Transactional(readOnly = true)
	@Override
	public Page<Estilo> filtrar(EstiloFilter filter, Pageable pageable) {
		
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Estilo.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		
		adicionarFiltro(filter, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filter));
	}
	
	private Long total(EstiloFilter filter) {
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Estilo.class);
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(EstiloFilter filter, Criteria criteria) {
		
		if (criteria != null) {
			
			if (!StringUtils.isEmpty(filter.getNome())) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
			}
			
		}
		
	}
	
}