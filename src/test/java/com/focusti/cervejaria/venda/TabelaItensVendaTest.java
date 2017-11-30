package com.focusti.cervejaria.venda;
	
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.focusti.cervejaria.model.Cerveja;
	
public class TabelaItensVendaTest {
	
	private TabelaItensVenda tabelaItensVenda;
	
	@Before
	public void setUp() {
		this.tabelaItensVenda = new TabelaItensVenda();
	}
	
	@Test
	public void deveCalcularValorTotalSemItens() throws Exception {
		assertEquals(BigDecimal.ZERO, this.tabelaItensVenda.getValorTotal());
	}
	
	@Test
	public void deveCalcularValorTotalComUmItem() throws Exception {
		
		Cerveja cerveja = new Cerveja();
		BigDecimal valor = new BigDecimal("8.90");
		cerveja.setValor(valor);
		
		this.tabelaItensVenda.adicionarItem(cerveja, 1);
		
		assertEquals(valor, this.tabelaItensVenda.getValorTotal());
		
	}
	
	@Test
	public void deveCalcularValorTotalComVariosItens() throws Exception {
		
		Cerveja c1 = new Cerveja();
		Cerveja c2 = new Cerveja();

		BigDecimal v1 = new BigDecimal("8.90");
		BigDecimal v2 = new BigDecimal("4.99");

		c1.setValor(v1);
		c2.setValor(v2);
		
		this.tabelaItensVenda.adicionarItem(c1, 1);
		this.tabelaItensVenda.adicionarItem(c2, 2);
		
		assertEquals(new BigDecimal("18.88"), this.tabelaItensVenda.getValorTotal());
		
	}
	
}