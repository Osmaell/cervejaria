package com.focusti.cervejaria.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.focusti.cervejaria.model.Cerveja;
import com.focusti.cervejaria.repository.helper.cerveja.CervejasQueries;

@Repository
public interface Cervejas extends JpaRepository<Cerveja, Long>, CervejasQueries {
	
	@Query("select c.sku as sku, c.nome as nome, c.descricao as descricao from Cerveja c")
	public Collection<SkuNomeDescricao> all();
	
}