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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import com.polo.webreservas.model.Cliente;
import com.polo.webreservas.model.Cliente.EstadoCliente;
import com.polo.webreservas.model.Inmueble;
import com.polo.webreservas.service.ClienteService;
import com.polo.webreservas.service.InmuebleService;
import com.polo.webreservas.service.ReservaService;
import com.polo.webreservas.util.PageRender;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cliente")
public class CatalogoController {
	@Autowired
	private InmuebleService inmuebleService;

	@Autowired
	private ClienteService servicio;

	@Autowired
	private ReservaService reservaService;

	@GetMapping("/home")
	public String homeCliente(Model model, Principal principal, HttpSession session) {

		String clienteEmail = principal.getName();
		Cliente cliente = servicio.findByCorreo(clienteEmail);

		if (cliente != null) {
			model.addAttribute("nombreCliente", cliente.getNombre() + " " + cliente.getApellido());
			if (cliente.getEstado() == EstadoCliente.sancionado) {
				model.addAttribute("modalSancion", true);
				model.addAttribute("alerta", "Su cuenta ha sido sancionada por infringir las normas del sistema.");
			} else {
				model.addAttribute("modalSancion", false);
			}
		} else {
			model.addAttribute("nombreCliente", "Desconocido");
			model.addAttribute("modalSancion", false);
		}
		return "cliente/home";
	}

	@GetMapping("/catalogo/verInmueble")
	public String verCatalogoInmuebles(@RequestParam(defaultValue = "0") int page,
			@RequestParam(name = "filtro", defaultValue = "") String filtro,
			@RequestParam(name = "precioDesde", required = false) Double precioDesde,
			@RequestParam(name = "precioHasta", required = false) Double precioHasta,
			@RequestParam(name = "fechaDesde", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
			@RequestParam(name = "fechaHasta", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
			@RequestParam(name = "estado", required = false) String estado,
			@RequestParam(name = "alerta", required = false) String alertaFlash,
			@RequestParam(name = "modalSancion", required = false) Boolean modalSancionFlash, Model model,
			Principal principal) {

		String correo = principal.getName();
		Cliente cliente = servicio.findByCorreo(correo);
		model.addAttribute("cliente", cliente);

		if (cliente.getEstado() == EstadoCliente.sancionado) {
			model.addAttribute("alerta", alertaFlash != null ? alertaFlash
					: "Su cuenta ha sido sancionada por infringir las normas. No puede visualizar inmuebles.");
			model.addAttribute("modalSancion", modalSancionFlash != null ? modalSancionFlash : true);
			model.addAttribute("motivo", "Su cuenta ha sido sancionada por infringir las normas del servicio.");
			return "cliente/catalogo/verInmueble";
		}

		// Cliente == activo
		PageRequest pageable = PageRequest.of(page, 5);

		Page<Inmueble> pagina = inmuebleService.listarConFiltrosAvanzados(filtro, precioDesde, precioHasta, fechaDesde,
				fechaHasta, estado, pageable);

		String urlBase = "/cliente/catalogo/verInmueble";
		List<String> params = new ArrayList<>();
		if (!filtro.isBlank())
			params.add("filtro=" + UriUtils.encode(filtro, StandardCharsets.UTF_8));
		if (precioDesde != null)
			params.add("precioDesde=" + precioDesde);
		if (precioHasta != null)
			params.add("precioHasta=" + precioHasta);
		if (fechaDesde != null)
			params.add("fechaDesde=" + fechaDesde);
		if (fechaHasta != null)
			params.add("fechaHasta=" + fechaHasta);
		if (estado != null && !estado.isBlank())
			params.add("estado=" + estado);

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

	@GetMapping("/catalogo/detalle/{id}")
	public String verDetalleInmueble(@PathVariable int id, Model modelo) {
		modelo.addAttribute("inmueble", inmuebleService.obtenerPorId(id));
		return "cliente/catalogo/DetalleInmueble";
	}

	@GetMapping("/ocupadas/{idInmueble}")
	@ResponseBody
	public List<LocalDate> obtenerFechasOcupadas(@PathVariable Long idInmueble) {
		return reservaService.obtenerFechasOcupadas(idInmueble);
	}

	@GetMapping("/catalogo/terminos")
	public String mostrarTerminosCondiciones() {
		return "cliente/catalogo/terminos";
	}

	@GetMapping("/catalogo/MotivoSancion")
	public String verMotivoSancion() {
		return "cliente/catalogo/MotivoSancion";
	}

}
