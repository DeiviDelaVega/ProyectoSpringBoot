package com.polo.webreservas.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.polo.webreservas.model.Cliente;
import com.polo.webreservas.model.Inmueble;
import com.polo.webreservas.model.Reserva;
import com.polo.webreservas.repository.ClienteRepository;
import com.polo.webreservas.repository.InmuebleRepository;
import com.polo.webreservas.repository.ReservaRepository;
import com.polo.webreservas.service.ReservaService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/pago")
public class PagoController {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private InmuebleRepository inmuebleRepository;

    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private ReservaService reservaService;
    
    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    @PostMapping("/crear-checkout-session")
    public String crearCheckoutSession(@RequestParam String fechaInicio,
                                       @RequestParam String fechaFin,
                                       @RequestParam String total,
                                       @RequestParam Integer inmuebleId,
                                       HttpSession session) throws StripeException {

        long amount = (long) (Double.parseDouble(total) * 100); // en centavos

        // Guardar datos en sesión
        session.setAttribute("fechaInicio", fechaInicio);
        session.setAttribute("fechaFin", fechaFin);
        session.setAttribute("total", total);
        session.setAttribute("inmuebleId", inmuebleId);

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:9090/pago/reserva-exitosa")
                .setCancelUrl("http://localhost:9090/pago/error")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("pen")
                                .setUnitAmount(amount)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Reserva de Inmueble")
                                        .build())
                                .build())
                        .build())
                .build();

        Session stripeSession = Session.create(params);
        return "redirect:" + stripeSession.getUrl();
    }

    @GetMapping("/reserva-exitosa")
    public String reservaExitosa(HttpSession session, Model model, Authentication auth) {
        String correo = auth.getName();
        Cliente cliente = clienteRepository.findByCorreo(correo);

        String fechaInicioStr = (String) session.getAttribute("fechaInicio");
        String fechaFinStr = (String) session.getAttribute("fechaFin");
        String totalStr = (String) session.getAttribute("total");
        Integer inmuebleId = (Integer) session.getAttribute("inmuebleId");

        if (fechaInicioStr == null || fechaFinStr == null || totalStr == null || inmuebleId == null || cliente == null) {
            model.addAttribute("error", "Error al recuperar los datos de la reserva.");
            return "error";
        }

        Inmueble inmueble = inmuebleRepository.findById(inmuebleId).orElseThrow();

        Reserva reserva = new Reserva();
        reserva.setFechaInicio(LocalDate.parse(fechaInicioStr));
        reserva.setFechaFin(LocalDate.parse(fechaFinStr));
        reserva.setMontoTotal(new BigDecimal(totalStr));
        reserva.setMetodoPago("Tarjeta");
        reserva.setEstadoReserva("Solicitado");
        reserva.setCliente(cliente);
        reserva.setInmueble(inmueble);

        reservaService.guardar(reserva);
        
        session.removeAttribute("fechaInicio");
        session.removeAttribute("fechaFin");
        session.removeAttribute("total");
        session.removeAttribute("inmuebleId");

        model.addAttribute("mensaje", "¡Reserva exitosa!");
        return "cliente/reserva-exitosa";
    }

    @GetMapping("/error")
    public String errorPago(Model model) {
        model.addAttribute("mensaje", "El pago fue cancelado o falló.");
        return "cliente/pago-error";
    }
}
