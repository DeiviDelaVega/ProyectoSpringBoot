package com.polo.webreservas.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.polo.webreservas.model.Reserva;
import com.polo.webreservas.service.ReservaService;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/cliente/misreservas")
public class MisReservasController {

    @Autowired
    private ReservaService reservaService;

    
    @GetMapping("/reembolso/{id}")
    public String procesarReembolso(@PathVariable Long id) {
        reservaService.eliminar(id); // Elimina directamente de la base de datos
        return "redirect:/cliente/misreservas/misReservas?eliminado=true";
    }
    
    @GetMapping("/misReservas")
    public String verMisReservas(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(name = "eliminado", required = false) Boolean eliminado,
                                 Principal principal) {

        String correoCliente = principal.getName();
        Pageable pageable = PageRequest.of(page, 5);
        Page<Reserva> reservas = reservaService.listarReservasPorCliente(correoCliente, pageable);

        model.addAttribute("reservas", reservas);
        model.addAttribute("page", reservas);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", reservas.getTotalPages());

        // 🔴 Aquí pasamos el valor al modelo si existe
        if (eliminado != null && eliminado) {
            model.addAttribute("eliminado", true);
        }

        return "cliente/misreservas/misReservas";
    }

    @GetMapping("/reserva-exitosa/{id}")
    public String mostrarReservaExitosa(@PathVariable Long id, Model model) {
        Optional<Reserva> reservaOpt = reservaService.obtenerPorId(id);
        if (reservaOpt.isPresent()) {
            Reserva reserva = reservaOpt.get();

            // 🔍 VERIFICACIÓN: log para confirmar que el cliente existe
            if (reserva.getCliente() == null) {
                System.out.println("⚠️ La reserva NO tiene cliente asociado");
            }

            model.addAttribute("reserva", reserva);
            return "cliente/reserva-exitosa";
        } else {
            return "redirect:/cliente/catalogo?errorReserva";
        }
    }





}
