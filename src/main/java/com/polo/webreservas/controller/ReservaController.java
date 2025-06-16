// ReservaController.java
package com.polo.webreservas.controller;

import com.polo.webreservas.model.Reserva;
import com.polo.webreservas.service.ReservaService;
import com.polo.webreservas.util.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/reserva")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/reservas")
    public String listarReservas(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Reserva> reservas = reservaService.listarTodas(pageRequest);
        PageRender<Reserva> pageRender = new PageRender<>("/admin/reserva/reservas", reservas);

        model.addAttribute("listReservas", reservas.getContent());
        model.addAttribute("reservas", reservas);
        model.addAttribute("page", pageRender);

        return "admin/reserva/MantReservas";
    }

    @GetMapping("/reservas/filtrar-estado")
    public String filtrarPorEstado(@RequestParam String estado, @RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Reserva> reservas = reservaService.filtrarPorEstado(estado, pageRequest);
        PageRender<Reserva> pageRender = new PageRender<>("/admin/reserva/reservas/filtrar-estado?estado=" + estado, reservas);

        model.addAttribute("listReservas", reservas.getContent());
        model.addAttribute("reservas", reservas);
        model.addAttribute("page", pageRender);
        model.addAttribute("estado", estado);

        return "admin/reserva/MantReservas";
    }

    @GetMapping("/reservas/filtrar-fechas")
    public String filtrarPorFechas(@RequestParam("fechaInicio") LocalDate fechaInicio,
                                   @RequestParam("fechaFin") LocalDate fechaFin,
                                   @RequestParam(defaultValue = "0") int page,
                                   Model model) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Reserva> reservas = reservaService.filtrarPorFechas(fechaInicio, fechaFin, pageRequest);
        PageRender<Reserva> pageRender = new PageRender<>("/admin/reserva/reservas/filtrar-fechas?fechaInicio=" + fechaInicio + "&fechaFin=" + fechaFin, reservas);

        model.addAttribute("listReservas", reservas.getContent());
        model.addAttribute("reservas", reservas);
        model.addAttribute("page", pageRender);
        model.addAttribute("fechaInicio", fechaInicio);
        model.addAttribute("fechaFin", fechaFin);

        return "admin/reserva/MantReservas";
    }

    
    @GetMapping("/reservas/detalle/{id}")
    public String detalleReserva(@PathVariable Long id, Model model) {
    	Reserva reserva = reservaService.obtenerPorId(id)
    		    .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        model.addAttribute("reserva", reserva);
        return "admin/reserva/DetalleReservas";
    }

    
 // POST para actualizar el estado de una reserva
    @PostMapping("/reservas/editar-estado/{id}")
    public String actualizarEstado(@PathVariable Long id, @RequestParam("estado") String nuevoEstado) {
        reservaService.actualizarEstado(id, nuevoEstado);
        return "redirect:/admin/reserva/reservas/detalle/" + id;
    }

    // GET para eliminar una reserva
    @GetMapping("/reservas/eliminar/{id}")
    public String eliminarReserva(@PathVariable Long id) {
        reservaService.eliminar(id);
        return "redirect:/admin/reserva/reservas";
    }


}
