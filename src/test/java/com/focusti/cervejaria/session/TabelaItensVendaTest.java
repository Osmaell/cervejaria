package com.focusti.cervejaria.session;
	
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.focusti.cervejaria.model.Cerveja;
import com.focusti.cervejaria.session.TabelaItensVenda;
	
public class TabelaItensVendaTest {
	
	private TabelaItensVenda tabelaItensVenda;
	
	@Before
	public void setUp() {
		this.tabelaItensVenda = new TabelaItensVenda("1");
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

		c1.setCodigo(1L);
		c2.setCodigo(2L);
		
		BigDecimal v1 = new BigDecimal("8.90");
		BigDecimal v2 = new BigDecimal("4.99");

		c1.setValor(v1);
		c2.setValor(v2);
		
		this.tabelaItensVenda.adicionarItem(c1, 1);
		this.tabelaItensVenda.adicionarItem(c2, 2);
		
		assertEquals(new BigDecimal("18.88"), this.tabelaItensVenda.getValorTotal());
		
	}
	
	@Test
	public void deveManterTamanhoDaListaParaMesmasCervejas() throws Exception {
		
		Cerveja c1 = new Cerveja();
		c1.setCodigo(1L);
		c1.setValor(new BigDecimal("4.50"));
		
		this.tabelaItensVenda.adicionarItem(c1, 1);
		this.tabelaItensVenda.adicionarItem(c1, 1);
		
		assertEquals(1, this.tabelaItensVenda.total());
		assertEquals(new BigDecimal("9.00"), this.tabelaItensVenda.getValorTotal());
		
	}
	
	@Test
	public void deveAlterarQuantidadeDoItem() throws Exception {
		
		Cerveja c1 = new Cerveja();
		c1.setCodigo(1L);
		c1.setValor(new BigDecimal("4.50"));
		
		this.tabelaItensVenda.adicionarItem(c1, 1);
		this.tabelaItensVenda.alterarQuantidadeItens(c1, 3);
		
		assertEquals(new BigDecimal("13.50"), this.tabelaItensVenda.getValorTotal());
		
	}
	
	@Test
	public void deveExcluirItem() throws Exception {
		
		Cerveja c1 = new Cerveja();
		Cerveja c2 = new Cerveja();
		Cerveja c3 = new Cerveja();

		c1.setCodigo(1L);
		c2.setCodigo(2L);
		c3.setCodigo(3L);

		c1.setValor(new BigDecimal("8.90"));
		c2.setValor(new BigDecimal("4.99"));
		c3.setValor(new BigDecimal("2.00"));
		
		
		this.tabelaItensVenda.adicionarItem(c1, 1);
		this.tabelaItensVenda.adicionarItem(c2, 2);
		this.tabelaItensVenda.adicionarItem(c3, 1);
		
		this.tabelaItensVenda.excluirItem(c2);
		
		assertEquals(2, this.tabelaItensVenda.total());
		assertEquals(new BigDecimal("10.90"), this.tabelaItensVenda.getValorTotal());
		
	}
	
}