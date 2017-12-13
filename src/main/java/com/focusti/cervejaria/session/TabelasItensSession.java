package com.focusti.cervejaria.session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.focusti.cervejaria.model.Cerveja;
import com.focusti.cervejaria.model.ItemVenda;

@SessionScope
@Component
public class TabelasItensSession {
	
	private Set<TabelaItensVenda> tabelas = new HashSet<>();
	
	public void adicionarItem(String uuid, Cerveja cerveja, int quantidade) {
		
		TabelaItensVenda tabela = buscarTabelaPorId(uuid);
		
		tabela.adicionarItem(cerveja, quantidade);
		tabelas.add(tabela);
		
	}
	
	public void alterarQuantidadeItens(String uuid, Cerveja cerveja, Integer quantidade) {
		TabelaItensVenda tabela = buscarTabelaPorId(uuid);
		tabela.alterarQuantidadeItens(cerveja, quantidade);
	}
	
	public void excluirItem(String uuid, Cerveja cerveja) {
		TabelaItensVenda tabela = buscarTabelaPorId(uuid);
		tabela.excluirItem(cerveja);
	}
	
	public List<ItemVenda> getItens(String uuid) {
		return buscarTabelaPorId(uuid).getItens();
	}
	
	private TabelaItensVenda buscarTabelaPorId(String uuid) {
		
		TabelaItensVenda tabela = tabelas.stream()
				.filter(t -> t.getUuid().equals(uuid))
				.findAny()
				.orElse(new TabelaItensVenda(uuid));
		
		return tabela;
	}
	
}