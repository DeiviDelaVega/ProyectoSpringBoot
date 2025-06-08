package com.polo.webreservas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.polo.webreservas.model.Administrador;
import com.polo.webreservas.model.Cliente;
import com.polo.webreservas.service.AdminService;
import com.polo.webreservas.service.ClienteService;

@Controller
@RequestMapping("/registro")
public class RegistroController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
    private AdminService adminService;
	
	@GetMapping
	public String mostrarFormulario(Model model) {
		 model.addAttribute("cliente", new Cliente());
		    return "registro";
	}
	
	 @PostMapping
	    public String registrar(@ModelAttribute Cliente cliente,
	                            @RequestParam String clave,
	                            RedirectAttributes redirectAttributes) throws DataIntegrityViolationException {
		 try {
		        clienteService.registrarCliente(cliente, clave);
		        redirectAttributes.addFlashAttribute("exito", "Registro exitoso.");
		        redirectAttributes.addFlashAttribute("correoEnviado", "Se ha enviado un correo de bienvenida.");
		    } catch (DataIntegrityViolationException e) {
		        redirectAttributes.addFlashAttribute("error", "⚠️ Ya existe un cliente con ese correo o número de documento.");
		    } catch (Exception e) {
		        redirectAttributes.addFlashAttribute("error", "❌ Hubo un problema en el registro: " + e.getMessage());
		    }

	        return "redirect:/registro";
	    }
	 
	 @GetMapping("/admin")
	    public String mostrarFormularioAdmin(Model model) {
	        model.addAttribute("admin", new Administrador());
	        return "registroAdmin"; 
	    }

	    @PostMapping("/admin")
	    public String registrarAdmin(@ModelAttribute Administrador admin,
	                                 @RequestParam String clave,
	                                 RedirectAttributes redirectAttributes) {
	        try {
	            adminService.registrarAdmin(admin, clave);
	            redirectAttributes.addFlashAttribute("exito", "Administrador registrado correctamente.");
	        } catch (DataIntegrityViolationException e) {
	            redirectAttributes.addFlashAttribute("error", "⚠️ Ya existe un administrador con ese correo o documento.");
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("error", "❌ Error: " + e.getMessage());
	        }
	        return "redirect:/registro/admin";
	    }
	 
	 
}
