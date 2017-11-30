package com.focusti.cervejaria.venda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.focusti.cervejaria.model.Cerveja;
import com.focusti.cervejaria.model.ItemVenda;

public class TabelaItensVenda {
	
	private List<ItemVenda> itens = new ArrayList<>();
	
	public BigDecimal getValorTotal() {
		
		return itens.stream()
				.map(ItemVenda::getValorTotal)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
		
	}
	
	public void adicionarItem(Cerveja cerveja, Integer quantidade) {
		
		ItemVenda itemVenda = new ItemVenda();
		itemVenda.setCerveja(cerveja);
		itemVenda.setQuantidade(quantidade);
		itemVenda.setValorUnitario(cerveja.getValor());
		
		this.itens.add(itemVenda);
		
	}
	
}