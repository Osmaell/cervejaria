package com.focusti.cervejaria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.focusti.cervejaria.model.constants.TipoPessoa;

@Controller
@RequestMapping("/clientes")
public class ClientesController {
	
	@GetMapping("/novo")
	public ModelAndView novo() {
		
		ModelAndView mv = new ModelAndView("cliente/CadastroCliente");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		
		return mv;
	}
	
}