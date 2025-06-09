package com.polo.webreservas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.polo.webreservas.model.Administrador;
import com.polo.webreservas.model.Inmueble;
import com.polo.webreservas.service.AdministradorService;
import com.polo.webreservas.service.InmuebleService;
import com.polo.webreservas.util.PageRender;
import org.springframework.ui.Model;

@Controller
public class InmuebleController {
	@Autowired
	private AdministradorService admiServicio;
	
	@Autowired
	private InmuebleService inmuServicio;
	
	@GetMapping({"/inmuebles", "/",""})
	public String listar(@RequestParam(name = "page",defaultValue = "0") int page, 
						@RequestParam(name = "filtro", required = false) String filtro, 
						@RequestParam(name = "disponibilidad", required = false) String disponibilidad,
						Model modelo) {
		Pageable pageRequest = PageRequest.of(page, 5);	// Cuantas filas con los datos de Inmueble van haber
		Page<Inmueble> inmuebles = inmuServicio.listarTodoConFiltroYDisponibilidad(filtro, disponibilidad, pageRequest);;
		PageRender<Inmueble> pageRender = new PageRender<>("/inmuebles", inmuebles);
		
		modelo.addAttribute("titulo","Lista de Inmuebles");
		modelo.addAttribute("inmuebles", inmuebles);
		modelo.addAttribute("page", pageRender);
		modelo.addAttribute("filtro", filtro); // Para mantener el valor en el input
		modelo.addAttribute("disponibilidad", disponibilidad); // Para mantener el valor en el select
		
		return "MantInmueble";	// Nos retorna al MantInmueble.html
	}
	
	@GetMapping("/inmuebles/nuevo")
	public String mostrarFormularioDeRegistro(Model modelo) {
		Inmueble inmueble = new Inmueble();
		inmueble.setAdministrador(new Administrador());
		modelo.addAttribute("inmueble", inmueble);
		modelo.addAttribute("administradores", admiServicio.listarTodo());
		return "CrearInmueble";
	}

	@PostMapping("/inmuebles")
	public String guardar(@ModelAttribute("inmueble") Inmueble inmueble) {
		inmuServicio.guardar(inmueble);
		return "redirect:/inmuebles";
	}
	
	@GetMapping("/inmuebles/detalle/{id}")
	public String verDetalleInmueble(@PathVariable int id, Model modelo) {
	    modelo.addAttribute("inmueble", inmuServicio.obtenerPorId(id));
	    modelo.addAttribute("administradores", admiServicio.listarTodo()); 
	    return "DetalleInmueble"; 
	}
	
	@GetMapping("/inmuebles/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable int id, Model modelo) {
		modelo.addAttribute("inmueble", inmuServicio.obtenerPorId(id));
		modelo.addAttribute("administradores", admiServicio.listarTodo());
		return "EditarInmueble";
	}

	@PostMapping("/inmuebles/{id}")
	public String actualizar(@PathVariable int id, @ModelAttribute("inmueble") Inmueble inmueble, Model modelo) {
		Inmueble inmuebleExistente = inmuServicio.obtenerPorId(id);
		inmuebleExistente.setId(id);
		inmuebleExistente.setNombre(inmueble.getNombre());
		inmuebleExistente.setCapacidad(inmueble.getCapacidad());
		inmuebleExistente.setNumeroHabitaciones(inmueble.getNumeroHabitaciones());
		inmuebleExistente.setDescripcion(inmueble.getDescripcion());
		inmuebleExistente.setServiciosIncluidos(inmueble.getServiciosIncluidos());
		inmuebleExistente.setDisponibilidad(inmueble.getDisponibilidad());
		inmuebleExistente.setPrecioPorNoche(inmueble.getPrecioPorNoche());
		inmuebleExistente.setImagenHabitacion(inmueble.getImagenHabitacion());
		inmuebleExistente.setAdministrador(inmueble.getAdministrador());
		
		int idAdmin = inmueble.getAdministrador().getId();
	    Administrador adminSeleccionado = admiServicio.obtenerPorId(idAdmin);
	    inmuebleExistente.setAdministrador(adminSeleccionado);
	    
		inmuServicio.actualizar(inmuebleExistente);
		return "redirect:/inmuebles";
	}

	@GetMapping("/inmuebles/{id}")
	public String eliminar(@PathVariable int id) {
		inmuServicio.eliminar(id);
		return "redirect:/inmuebles";
	}
}