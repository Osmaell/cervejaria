package com.focusti.cervejaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.focusti.cervejaria.model.Grupo;

@Repository
public interface Grupos extends JpaRepository<Grupo, Long> {
	
}