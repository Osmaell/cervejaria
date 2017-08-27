package com.focusti.cervejaria.controller;
	
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.focusti.cervejaria.controller.page.PageWrapper;
import com.focusti.cervejaria.model.Estilo;
import com.focusti.cervejaria.repository.Estilos;
import com.focusti.cervejaria.repository.filter.EstiloFilter;
import com.focusti.cervejaria.service.EstiloService;
import com.focusti.cervejaria.service.exception.NomeEstiloJaCadastradoException;
	
@Controller
@RequestMapping("/estilos")
public class EstilosController {
	
	private static final String CADASTRO_ESTILO_VIEW = "estilo/CadastroEstilo";
	
	@Autowired
	private EstiloService estiloService;
	
	@Autowired
	private Estilos estilos;
	
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
		} catch (NomeEstiloJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(estilo);
		}
		
	}
	
	@GetMapping
	public ModelAndView pesquisar(EstiloFilter estiloFilter, BindingResult result,
			@PageableDefault(size = 2) Pageable pageable, HttpServletRequest httpServletRequest) {
		
		ModelAndView mv = new ModelAndView("estilo/PesquisaEstilo");
		
		Page<Estilo> page = estilos.filtrar(estiloFilter, pageable);
		PageWrapper<Estilo> pagina = new PageWrapper<>(page, httpServletRequest);
		
		mv.addObject("pagina", pagina);
		
		return mv;
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody @Valid Estilo estilo, BindingResult result) {
		
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());
		}
		
		estiloService.salvar(estilo);
		
		return ResponseEntity.ok().body(estilo);
	}
	
}