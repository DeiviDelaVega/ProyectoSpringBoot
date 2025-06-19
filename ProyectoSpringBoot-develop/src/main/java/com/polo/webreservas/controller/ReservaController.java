package com.polo.webreservas.controller;

import com.polo.webreservas.model.Reserva;
import com.polo.webreservas.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public String listarReservas(
            @RequestParam(value = "fechaInicio", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,

            @RequestParam(value = "fechaFin", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,

            Pageable pageable,
            Model model) {

        Page<Reserva> reservas;

        if (fechaInicio != null && fechaFin != null) {
            reservas = reservaService.BuscarPorFechas(fechaInicio, fechaFin, pageable);
        } else {
            reservas = reservaService.listarTodas(pageable);
        }

        model.addAttribute("reservas", reservas);
        model.addAttribute("fechaInicioSeleccionada", fechaInicio);
        model.addAttribute("fechaFinSeleccionada", fechaFin);

        return "reserva/lista";
    }
}
