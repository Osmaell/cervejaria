package com.focusti.cervejaria.controller;
	
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.focusti.cervejaria.model.Estilo;
import com.focusti.cervejaria.service.EstiloService;
import com.focusti.cervejaria.service.exception.NomeJaCadastradoException;
	
@Controller
@RequestMapping("/estilos")
public class EstilosController {
	
	private static final String CADASTRO_ESTILO_VIEW = "estilo/CadastroEstilo";
	
	@Autowired
	private EstiloService estiloService;
	
	@GetMapping("/novo")
	public ModelAndView novo(Estilo estilo) {
		ModelAndView mv = new ModelAndView(CADASTRO_ESTILO_VIEW);
		return mv;
	}
	
	@PostMapping
	public ModelAndView salvar(@Valid Estilo estilo, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return novo(estilo);
		}
		
		try {
			estiloService.salvar(estilo);
			attributes.addFlashAttribute("mensagem", "Estilo salvo com sucesso!");
			return new ModelAndView("redirect:/estilos/novo");
		} catch (NomeJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(estilo);
		}
		
	}
	
}