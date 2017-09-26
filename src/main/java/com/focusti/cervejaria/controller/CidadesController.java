package com.focusti.cervejaria.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.focusti.cervejaria.controller.page.PageWrapper;
import com.focusti.cervejaria.model.Cidade;
import com.focusti.cervejaria.repository.Cidades;
import com.focusti.cervejaria.repository.Estados;
import com.focusti.cervejaria.repository.filter.CidadeFilter;
import com.focusti.cervejaria.service.CidadeService;
import com.focusti.cervejaria.service.exception.CidadeJaCadastradaException;

@Controller
@RequestMapping("/cidades")
public class CidadesController {
	
	@Autowired
	private Cidades cidades;
	
	@Autowired
	private Estados estados;
	
	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping("/nova")
	public ModelAndView nova(Cidade cidade) {
		ModelAndView mv = new ModelAndView("cidade/CadastroCidade");
		mv.addObject("estados", estados.findAll());
		return mv;
	}
	
	@PostMapping
	public ModelAndView salvar(@Valid Cidade cidade, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return nova(cidade);
		}
		
		try {
			cidadeService.salvar(cidade);
			attributes.addFlashAttribute("mensagem", "Cidade salva com sucesso!");
		} catch (CidadeJaCadastradaException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return nova(cidade);
		}
		
		return new ModelAndView("redirect:/cidades/nova");
		
	}
	
	@GetMapping
	public ModelAndView pesquisar(CidadeFilter cidadeFilter, BindingResult result,@PageableDefault(size = 10)  Pageable pageable, HttpServletRequest  httpServletRequest) {
		
		ModelAndView mv = new ModelAndView("cidade/PesquisaCidade");
		
		Page<Cidade> page = cidades.filtrar(pageable, cidadeFilter);
		PageWrapper<Cidade> pageWrapper = new PageWrapper<>(page, httpServletRequest);
		
		mv.addObject("estados", estados.findAll());
		mv.addObject("pagina", pageWrapper);
		
		return mv;
	}
	
	@GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisarPorCodigoEstado( @RequestParam(name = "estado", defaultValue = "-1") Long codigoEstado) {
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		
		return this.cidades.findByEstadoCodigo(codigoEstado);
	}
	
}