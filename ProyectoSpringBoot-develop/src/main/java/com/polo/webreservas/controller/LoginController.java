package com.polo.webreservas.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String login(Authentication auth) {
		if(auth != null && auth.isAuthenticated()) {
			return "redirect:/default";
		}
		return "login";
	}
	
	@GetMapping("/default") 
	public String redirigir(Authentication auth) {
		if(auth.getAuthorities().toString().contains("ROLE_admin")) {
			return "redirect:/admin/home";
		} else if(auth.getAuthorities().toString().contains("ROLE_cliente")) {
			return "redirect:/cliente/home";
		}
		return "redirect:/login?error";
	}

}