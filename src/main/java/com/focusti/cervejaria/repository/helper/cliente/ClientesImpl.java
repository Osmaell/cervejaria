package com.focusti.cervejaria.repository.helper.cliente;
	
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.focusti.cervejaria.model.Cliente;
import com.focusti.cervejaria.repository.filter.ClienteFilter;
import com.focusti.cervejaria.repository.paginacao.PaginacaoUtil;
	
public class ClientesImpl implements ClientesQueries {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> listar() {
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Cliente.class);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Cliente> filtrar(Pageable pageable, ClienteFilter filter) {
		
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Cliente.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		
		adicionarFiltro(filter, criteria);
		
		criteria.createAlias("endereco.cidade", "c", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("c.estado", "e", JoinType.LEFT_OUTER_JOIN);
		
		return new PageImpl<>(criteria.list(), pageable, total(filter));
	}
	
	private Long total(ClienteFilter filter) {
		
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Cliente.class);
		
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(ClienteFilter filter, Criteria criteria) {
		
		if (criteria != null) {
			
			if (!StringUtils.isEmpty(filter.getNome())) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(filter.getCpfOuCnpj())) {
				criteria.add(Restrictions.eq("cpfOuCnpj", filter.getCpfOuCnpj()));
			}
			
		}
		
	}
	
}