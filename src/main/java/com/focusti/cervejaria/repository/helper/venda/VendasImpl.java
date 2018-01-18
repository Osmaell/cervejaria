package com.focusti.cervejaria.repository.helper.venda;
	
import java.time.LocalDateTime;
import java.time.LocalTime;

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
import org.springframework.util.StringUtils;

import com.focusti.cervejaria.model.Venda;
import com.focusti.cervejaria.repository.filter.VendaFilter;
import com.focusti.cervejaria.repository.paginacao.PaginacaoUtil;
	
public class VendasImpl implements VendasQueries {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<Venda> filtrar(VendaFilter filtro, Pageable pageable) {
		
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Venda.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		
		adicionarFiltro(criteria, filtro);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	private Long total(VendaFilter filtro) {
		
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Venda.class);
		adicionarFiltro(criteria, filtro);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(Criteria criteria, VendaFilter filtro) {
		
		criteria.createAlias("cliente", "c");
		
		if (criteria != null) {
			
			if (filtro.getCodigo() != null) {
				criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
			}
			
			if (filtro.getStatus() != null) {
				criteria.add(Restrictions.eq("status", filtro.getStatus()));
			}
			
			if (filtro.getValorMinimo() != null) {
				criteria.add(Restrictions.ge("valorTotal", filtro.getValorMinimo()));
			}
			
			if (filtro.getValorMaximo() != null) {
				criteria.add(Restrictions.le("valorTotal", filtro.getValorMaximo()));
			}
			
			if (filtro.getDesde() != null) {
				LocalDateTime desde = LocalDateTime.of(filtro.getDesde(), LocalTime.of(0, 0));
				criteria.add(Restrictions.ge("dataCriacao", desde));
			}
			
			if (filtro.getAte() != null) {
				LocalDateTime ate = LocalDateTime.of(filtro.getAte(), LocalTime.of(23, 59));
				criteria.add(Restrictions.le("dataCriacao", ate));
			}
			
			if (!StringUtils.isEmpty(filtro.getNomeCliente())) {
				criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCliente(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(filtro.getCpfOuCnpjCliente())) {
				criteria.add(Restrictions.eq("c.cpfOuCnpj", filtro.getCpfOuCnpjCliente()));
			}
			
		}
		
	}
	
}