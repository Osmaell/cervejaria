package com.focusti.cervejaria.controller;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.focusti.cervejaria.model.Cerveja;
import com.focusti.cervejaria.repository.Cervejas;
import com.focusti.cervejaria.session.TabelaItensVenda;
	
@Controller
@RequestMapping("/vendas")
public class VendasController {
	
	private static final String CADASTRO_VENDA_VIEW = "venda/CadastroVenda";
	
	@Autowired
	private Cervejas cervejas;
	
	@Autowired
	private TabelaItensVenda tabelaItensVenda;
	
	@GetMapping("/nova")
	public ModelAndView nova() {
		
		ModelAndView mv = new ModelAndView(CADASTRO_VENDA_VIEW);
		
		return mv;
	}
	
	@PostMapping("/item")
	public @ResponseBody ModelAndView adicionarItem(Long codigoCerveja) {
		
		Cerveja cerveja = cervejas.findOne(codigoCerveja);
		tabelaItensVenda.adicionarItem(cerveja, 1);
		
		return mvTabelaItensVenda();
	}
	
	@PutMapping("/item/{codigoCerveja}")
	public ModelAndView alterarQuantidadeItem(@PathVariable("codigoCerveja") Cerveja cerveja, Integer quantidade) {
		
		this.tabelaItensVenda.alterarQuantidadeItens(cerveja, quantidade);
		
		return mvTabelaItensVenda();
	}
	
	@DeleteMapping("/item/{codigoCerveja}")
	public ModelAndView excluir( @PathVariable("codigoCerveja") Cerveja cerveja ) {
		
		this.tabelaItensVenda.excluirItem(cerveja);
		
		return mvTabelaItensVenda();
	}
	
	private ModelAndView mvTabelaItensVenda() {
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		mv.addObject("itens", tabelaItensVenda.getItens());
		return mv;
	}
	
}