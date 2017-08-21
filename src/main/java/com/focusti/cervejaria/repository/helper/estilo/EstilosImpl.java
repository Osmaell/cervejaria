package com.focusti.cervejaria.repository.helper.estilo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import com.focusti.cervejaria.model.Estilo;

public class EstilosImpl implements EstilosQueries {

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Estilo> todos() {
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Estilo.class);
		return criteria.list();
	}
	
}
