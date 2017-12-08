package com.focusti.cervejaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
		
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		mv.addObject("itens", tabelaItensVenda.getItens());
		
		return mv;
	}
	
}