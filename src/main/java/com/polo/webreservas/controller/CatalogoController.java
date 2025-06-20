package com.polo.webreservas.controller;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import com.polo.webreservas.model.Cliente;
import com.polo.webreservas.model.Inmueble;
import com.polo.webreservas.service.ClienteService;
import com.polo.webreservas.service.InmuebleService;
import com.polo.webreservas.util.PageRender;


@Controller
@RequestMapping("/cliente")
public class CatalogoController {
	@Autowired
	private InmuebleService inmuebleService;
	
	@Autowired
	private ClienteService servicio;

	@GetMapping("/home")
    public String homeCliente(Model model, Principal principal) {
        String clienteEmail = principal.getName();
        Cliente cliente = servicio.findByCorreo(clienteEmail);
        if (cliente != null) {
            model.addAttribute("nombreCliente", cliente.getNombre() + " " + cliente.getApellido());
        } else {
            model.addAttribute("nombreCliente", "Desconocido");
        }
        return "cliente/home";
    }

	@GetMapping("/catalogo/verInmueble")
	public String verCatalogoInmuebles(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(name = "filtro", defaultValue = "") String filtro,
	        @RequestParam(name = "precioDesde", required = false) Double precioDesde,
	        @RequestParam(name = "precioHasta", required = false) Double precioHasta,
	        @RequestParam(name = "fechaDesde", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
	        @RequestParam(name = "fechaHasta", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
	        @RequestParam(name = "estado", required = false) String estado,
	        Model model) {

	    PageRequest pageable = PageRequest.of(page, 5);

	    Page<Inmueble> pagina = inmuebleService.listarConFiltrosAvanzados(
	            filtro, precioDesde, precioHasta, fechaDesde, fechaHasta, estado, pageable);

	    String urlBase = "/catalogo/verInmueble";
	    List<String> params = new ArrayList<>();
	    if (!filtro.isBlank()) params.add("filtro=" + UriUtils.encode(filtro, StandardCharsets.UTF_8));
	    if (precioDesde != null) params.add("precioDesde=" + precioDesde);
	    if (precioHasta != null) params.add("precioHasta=" + precioHasta);
	    if (fechaDesde != null) params.add("fechaDesde=" + fechaDesde);
	    if (fechaHasta != null) params.add("fechaHasta=" + fechaHasta);
	    if (estado != null && !estado.isBlank()) params.add("estado=" + estado);

	    if (!params.isEmpty()) {
	        urlBase += "?" + String.join("&", params);
	    }

	    PageRender<Inmueble> pageRender = new PageRender<>(urlBase, pagina);

	    model.addAttribute("inmuebles", pagina.getContent());
	    model.addAttribute("page", pageRender);
	    model.addAttribute("filtro", filtro);
	    model.addAttribute("titulo", "Catálogo de Inmuebles");
	    
	    if (pagina.isEmpty()) {
	        String mensaje = "No se encontraron inmuebles con los filtros ingresados.";
	        model.addAttribute("alerta", mensaje);
	    }
	    
	    return "cliente/catalogo/verInmueble";
	}

}
