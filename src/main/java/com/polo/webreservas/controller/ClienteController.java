package com.polo.webreservas.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.polo.webreservas.model.Cliente;
import com.polo.webreservas.service.ClienteService;
import org.springframework.ui.Model;

@Controller
public class ClienteController {
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/cliente/home")
	public String homeCliente(Model model, Principal principal) {
	    String clienteEmail = principal.getName();
	    Cliente cliente = clienteService.findByCorreo(clienteEmail);
	    if (cliente != null) {
	        model.addAttribute("nombreCliente", cliente.getNombre() + " " + cliente.getApellido());
	    } else {
	        model.addAttribute("nombreCliente", "Desconocido");
	    }
	    return "cliente/home";
	}

}
