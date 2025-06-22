package com.polo.webreservas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.polo.webreservas.service.ReservaService;

@Controller
@RequestMapping("/admin/reportes")
public class AdminReportesController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/InmueblesMasReservados")
    public String verInmueblesMasReservados(Model model) {
        List<Object[]> datos = reservaService.obtenerInmueblesMasReservados();
        model.addAttribute("datosInmuebles", datos);
        return "admin/reportes/InmueblesMasReservados";
    }
}
