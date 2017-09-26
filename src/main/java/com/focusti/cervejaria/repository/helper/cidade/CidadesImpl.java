package com.focusti.cervejaria.repository.helper.cidade;

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
import org.thymeleaf.util.StringUtils;

import com.focusti.cervejaria.model.Cidade;
import com.focusti.cervejaria.repository.filter.CidadeFilter;
import com.focusti.cervejaria.repository.paginacao.PaginacaoUtil;

public class CidadesImpl implements CidadesQueries {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<Cidade> buscar() {
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Cidade.class);
		return criteria.list();
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public Page<Cidade> filtrar(Pageable pageable, CidadeFilter filter) {
		
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Cidade.class);
		
		adicionarFiltro(criteria, filter);
		
		paginacaoUtil.preparar(criteria, pageable);
		
		return new PageImpl<>(criteria.list(), pageable, total(filter));
	}
	
	private Long total(CidadeFilter filter) {
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Cidade.class);
		adicionarFiltro(criteria, filter);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(Criteria criteria, CidadeFilter filter) {
		
		if (criteria != null) {
			
			if (isEstadoPresente(filter)) {
				criteria.add(Restrictions.eq("estado", filter.getEstado()));
			}
			
			if (!StringUtils.isEmpty(filter.getNome())) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
			}
			
		}
		
	}
	
	private boolean isEstadoPresente(CidadeFilter filter) {
		return filter.getEstado() != null && filter.getEstado().getCodigo() != null;
	}
	
}