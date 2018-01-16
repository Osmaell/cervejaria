package com.focusti.cervejaria.service;
	
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focusti.cervejaria.model.Venda;
import com.focusti.cervejaria.model.constants.StatusVenda;
import com.focusti.cervejaria.repository.Vendas;
	
@Service
public class VendaService {
	
	@Autowired
	private Vendas vendas;
	
	public void salvar(Venda venda) {
		
		if(venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
		}
		
		if (venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(
					venda.getDataEntrega(),
					venda.getHorarioEntrega() != null ? venda.getHorarioEntrega() : LocalTime.NOON));
		}
		
		this.vendas.save(venda);
	}
	
	public void emitir(Venda venda) {
		venda.setStatus(StatusVenda.EMITIDA);
		salvar(venda);
	}
	
}