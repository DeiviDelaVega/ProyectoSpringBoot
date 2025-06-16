package com.polo.webreservas.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.polo.webreservas.model.Administrador;
import com.polo.webreservas.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private final AdminService adminService;
    
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/home")
    public String adminHome(Model model, Principal principal) {
        String adminEmail = principal.getName(); 
        Administrador admin = adminService.findByCorreo(adminEmail); 

        if (admin != null) {
            model.addAttribute("nombreAdministrador", admin.getNombre() + " " + admin.getApellido());
        } else {
            model.addAttribute("nombreAdministrador", "Desconocido"); 
        }
        return "admin/home";
    }
}
