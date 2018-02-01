package com.focusti.cervejaria.controller;
	
import java.util.List;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.focusti.cervejaria.controller.page.PageWrapper;
import com.focusti.cervejaria.dto.CervejaDTO;
import com.focusti.cervejaria.model.Cerveja;
import com.focusti.cervejaria.model.constants.Origem;
import com.focusti.cervejaria.model.constants.Sabor;
import com.focusti.cervejaria.repository.Cervejas;
import com.focusti.cervejaria.repository.Estilos;
import com.focusti.cervejaria.repository.filter.CervejaFilter;
import com.focusti.cervejaria.service.CervejaService;
import com.focusti.cervejaria.service.exception.ImpossivelExcluirEntidadeException;
	
@Controller
@RequestMapping("/cervejas")
public class CervejasController {
	
	private static final String CADASTRO_CERVEJA_VIEW = "cerveja/CadastroCerveja";
	
	@Autowired
	private CervejaService cervejaService;
	
	@Autowired
	private Estilos estilos;
	
	@Autowired
	private Cervejas cervejas;
	
	@GetMapping("/nova")
	public ModelAndView nova(Cerveja cerveja) {
		
		ModelAndView mv = new ModelAndView(CADASTRO_CERVEJA_VIEW);
		mv.addObject("sabores", Sabor.values());
		mv.addObject("origens", Origem.values());
		mv.addObject("estilos", estilos.listar());
		return mv;
	}
	
	@PostMapping
	public ModelAndView salvar(@Valid Cerveja cerveja, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return nova(cerveja);
		}
		
		cervejaService.salvar(cerveja);
		attributes.addFlashAttribute("mensagem", "Cerveja salva com sucesso");
		
		return new ModelAndView("redirect:/cervejas/nova");
	}
	
	@GetMapping
	public ModelAndView pesquisar(CervejaFilter cervejaFilter, @PageableDefault(size = 2) Pageable pageable, 
			HttpServletRequest httpServletRequest) {
		
		ModelAndView mv = new ModelAndView("cerveja/PesquisaCerveja");
		
		mv.addObject("estilos", estilos.listar());
		mv.addObject("sabores", Sabor.values());
		mv.addObject("origens", Origem.values());
		
		Page<Cerveja> pagina = cervejas.filtar(cervejaFilter, pageable);
		PageWrapper<Cerveja> pageWrapper = new PageWrapper<>(pagina, httpServletRequest);
		
		mv.addObject("pagina", pageWrapper);
		
		return mv;
	}
	
	@GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<CervejaDTO> pesquisar(String skuOuNome) {
		return cervejas.porSkuOuNome(skuOuNome);
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir( @PathVariable Long codigo ) {
		
		try {
			cervejaService.excluir(codigo);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar( @PathVariable("codigo") Cerveja cerveja ) {
		ModelAndView mv = nova(cerveja);
		mv.addObject(cerveja);
		return mv;
	}
	
}