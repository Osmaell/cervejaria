package com.focusti.cervejaria.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.focusti.cervejaria.controller.page.PageWrapper;
import com.focusti.cervejaria.model.Cliente;
import com.focusti.cervejaria.model.constants.TipoPessoa;
import com.focusti.cervejaria.repository.Clientes;
import com.focusti.cervejaria.repository.Estados;
import com.focusti.cervejaria.repository.filter.ClienteFilter;
import com.focusti.cervejaria.service.ClienteService;
import com.focusti.cervejaria.service.exception.CpfCnpjJaCadastrado;

@Controller
@RequestMapping("/clientes")
public class ClientesController {
	
	@Autowired
	private Estados estados;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private Clientes clientes;
	
	@GetMapping("/novo")
	public ModelAndView novo(Cliente cliente) {
		
		ModelAndView mv = new ModelAndView("cliente/CadastroCliente");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("estados", estados.findAll());
		
		return mv;
	}
	
	@PostMapping
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return novo(cliente);
		}
		
		try {
		
			clienteService.salvar(cliente);
			attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso!");
			
		} catch (CpfCnpjJaCadastrado e) {
			result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
			return novo(cliente);
		}
		
		return new ModelAndView("redirect:/clientes/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(ClienteFilter clienteFilter, BindingResult result , @PageableDefault(size=10) Pageable pageable, HttpServletRequest httpServletRequest) {
		
		ModelAndView mv = new ModelAndView("cliente/PesquisaCliente");
		
		Page<Cliente> page = clientes.filtrar(pageable, clienteFilter);
		PageWrapper<Cliente> pageWrapper = new PageWrapper<>(page, httpServletRequest);
		
		mv.addObject("pagina", pageWrapper);
		
		return mv;
	}
	
}