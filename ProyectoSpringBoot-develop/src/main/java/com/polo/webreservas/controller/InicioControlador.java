package com.polo.webreservas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioControlador {
	@GetMapping("/inicio")
	
	public String inicio() {
	    return "inicio";
	}

}
