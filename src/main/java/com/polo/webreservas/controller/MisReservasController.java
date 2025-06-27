package com.polo.webreservas.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.polo.webreservas.model.Pago;
import com.polo.webreservas.model.Reserva;
import com.polo.webreservas.service.EmailService;
import com.polo.webreservas.service.PagoService;
import com.polo.webreservas.service.ReservaService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/cliente/misreservas")
public class MisReservasController {

    @Autowired
    private ReservaService reservaService;
    
    @Autowired
    private PagoService pagoService;

    @Autowired
    private EmailService emailService;

    @Value("${admin.email}")
    private String correoAdmin;


    
    @GetMapping("/reembolso/{id}")
    @ResponseBody
    public ResponseEntity<String> procesarReembolso(@PathVariable Long id) {
        try {
            Optional<Reserva> reservaOpt = reservaService.obtenerPorId(id);

            if (reservaOpt.isPresent()) {
                Reserva reserva = reservaOpt.get();
                Pago pago = pagoService.buscarPorReserva(reserva);

                if (pago != null) {
                    pagoService.eliminar(pago);
                }

                reservaService.eliminar(id);

                try {
                    emailService.enviarReembolsoAdmin(correoAdmin, reserva, pago);
                } catch (Exception e) {
                    System.err.println("Error al enviar correo de reembolso: " + e.getMessage());
                }

                return ResponseEntity.ok("OK");
            } else {
                return ResponseEntity.status(404).body("No encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error");
        }
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

        List<Map<String, Object>> paginas = new ArrayList<>();
        int totalPaginas = reservas.getTotalPages();
        for (int i = 1; i <= totalPaginas; i++) {
            Map<String, Object> pagina = new HashMap<>();
            pagina.put("numero", i);
            pagina.put("actual", (i == page + 1)); 
            paginas.add(pagina);
        }

        Map<String, Object> pageWrapper = new HashMap<>();
        pageWrapper.put("hasPrevius", reservas.hasPrevious()); 
        pageWrapper.put("hasNext", reservas.hasNext());
        pageWrapper.put("paginaActual", reservas.getNumber() + 1); 
        pageWrapper.put("totalPaginas", reservas.getTotalPages());
        pageWrapper.put("first", reservas.isFirst());
        pageWrapper.put("last", reservas.isLast());
        pageWrapper.put("url", "");
        pageWrapper.put("paginas", paginas); 

        model.addAttribute("page", pageWrapper);

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
