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

import com.focusti.cervejaria.model.Usuario;
import com.focusti.cervejaria.service.UsuarioService;
import com.focusti.cervejaria.service.exception.UsuarioJaCadastradoException;

@Controller
@RequestMapping("usuarios")
public class UsuariosController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		return mv;
	}
	
	@PostMapping
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return novo(usuario);
		}
		
		try {
			usuarioService.salvar(usuario);
			attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso");
		} catch (UsuarioJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(usuario);
		}
		
		return new ModelAndView("redirect:/usuarios/novo");
	}
	
}