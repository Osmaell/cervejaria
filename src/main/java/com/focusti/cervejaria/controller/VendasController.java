package com.focusti.cervejaria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/vendas")
public class VendasController {

	private static final String CADASTRO_VENDA_VIEW = "venda/CadastroVenda";

	@GetMapping("/nova")
	public ModelAndView nova() {
		
		ModelAndView mv = new ModelAndView(CADASTRO_VENDA_VIEW);
		
		return mv;
	}
	
}
