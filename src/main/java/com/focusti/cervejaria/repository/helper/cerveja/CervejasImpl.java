package com.focusti.cervejaria.repository.helper.cerveja;
	
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.focusti.cervejaria.model.Cerveja;
import com.focusti.cervejaria.repository.filter.CervejaFilter;
	
public class CervejasImpl implements CervejasQueries {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Cerveja> listar() {
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Cerveja.class);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Cerveja> filtar(CervejaFilter filter, Pageable pageable) {
		
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Cerveja.class);
		
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
		
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistrosPorPagina);
		
		Sort sort = pageable.getSort();
		if (sort != null) {
			
			Sort.Order order = sort.iterator().next();
			String property = order.getProperty();
			criteria.addOrder(order.isAscending() ? Order.asc(property) : Order.desc(property));
			
		}
		
		adicionarFiltro(filter, criteria);
		
		// conteúdo, pageable e total de páginas que vai ser calculado
		// de acordo com o filtro
		return new PageImpl<>(criteria.list(), pageable, total(filter));
	}

	private Long total(CervejaFilter filter) {
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Cerveja.class);
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(CervejaFilter filter, Criteria criteria) {
		
		if (filter != null) {
			
			if (!StringUtils.isEmpty(filter.getSku())) {
				criteria.add(Restrictions.eq("sku", filter.getSku()));
			}
			
			if (!StringUtils.isEmpty(filter.getNome())) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
			}
			
			if (isEstiloPresent(filter)) {
				criteria.add(Restrictions.eq("estilo", filter.getEstilo()));
			}
			
			if (filter.getSabor() != null) {
				criteria.add(Restrictions.eq("sabor", filter.getSabor()));
			}
			
			if (filter.getOrigem() != null) {
				criteria.add(Restrictions.eq("origem", filter.getOrigem()));
			}
			
			if (filter.getValorDe() != null) {
				criteria.add(Restrictions.ge("valor", filter.getValorDe()));
			}
			
			if (filter.getValorAte() != null) {
				criteria.add(Restrictions.le("valor", filter.getValorAte()));
			}
			
		}
		
	}
	
	private boolean isEstiloPresent(CervejaFilter filter) {
		return filter.getEstilo() != null && filter.getEstilo().getCodigo() != null;
	}
	
}