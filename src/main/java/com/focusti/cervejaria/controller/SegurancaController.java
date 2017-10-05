package com.focusti.cervejaria.controller;
	
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
	
@Controller
public class SegurancaController {

	@GetMapping("/login")
	public ModelAndView login( @AuthenticationPrincipal User user) {
		
		ModelAndView mv = new ModelAndView("login");
		
		if(user != null) {
			return new ModelAndView("redirect:/cervejas");
		}
		
		return mv;
	}
	
}
