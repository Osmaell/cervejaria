package com.focusti.cervejaria.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.focusti.cervejaria.model.ItemVenda;
import com.focusti.cervejaria.model.Venda;
import com.focusti.cervejaria.repository.Vendas;

@Service
public class VendaService {
	
	@Autowired
	private Vendas vendas;
	
	public void salvar(Venda venda) {
		
		if(venda.isNova()) {
			
			BigDecimal valorTotalItens = venda.getItens().stream()
									.map(ItemVenda::getValorTotal)
									.reduce(BigDecimal::add)
									.get();
			
			BigDecimal valorTotal = calcularValorTotal(valorTotalItens, venda.getValorFrete(), venda.getValorDesconto());
			venda.setValorTotal(valorTotal);
			venda.setDataCriacao(LocalDateTime.now());
			
			if (venda.getDataEntrega() != null) {
				venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(), venda.getHorarioEntrega()));
			}
			
		}
		
		this.vendas.save(venda);
	}
	
	private BigDecimal calcularValorTotal(BigDecimal valorTotalItens, BigDecimal valorFrete, BigDecimal valorDesconto) {
		
		BigDecimal valorTotal = valorTotalItens
				.add(Optional.ofNullable(valorFrete).orElse(BigDecimal.ZERO))
				.subtract(Optional.ofNullable(valorDesconto).orElse(BigDecimal.ZERO));
		
		return valorTotal;
	}
	
}