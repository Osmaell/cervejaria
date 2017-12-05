package com.focusti.cervejaria.session;
	
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.focusti.cervejaria.model.Cerveja;
import com.focusti.cervejaria.model.ItemVenda;

@SessionScope
@Component
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
	
	public int total() {
		return itens.size();
	}
	
}