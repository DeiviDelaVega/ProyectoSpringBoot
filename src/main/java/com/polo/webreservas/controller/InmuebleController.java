package com.polo.webreservas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.polo.webreservas.model.Administrador;
import com.polo.webreservas.model.Inmueble;
import com.polo.webreservas.service.AdminService;
import com.polo.webreservas.service.CloudinaryService;
import com.polo.webreservas.service.InmuebleService;
import com.polo.webreservas.util.PageRender;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/admin/inmueble")
public class InmuebleController {
	@Autowired
	private AdminService admiService;

	@Autowired
	private InmuebleService inmuService;

	@Autowired
	private CloudinaryService clouService;

	@GetMapping("/inmuebles")
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "filtro", required = false) String filtro,
			@RequestParam(name = "disponibilidad", required = false) String disponibilidad, Model modelo) {
		Pageable pageRequest = PageRequest.of(page, 5); // Cuantas filas con los datos de Inmueble van haber
		Page<Inmueble> inmuebles = inmuService.listarTodoConFiltroYDisponibilidad(filtro, disponibilidad, pageRequest);
		;
		PageRender<Inmueble> pageRender = new PageRender<>("/admin/inmueble/inmuebles", inmuebles);

		modelo.addAttribute("titulo", "Lista de Inmuebles");
		modelo.addAttribute("inmuebles", inmuebles);
		modelo.addAttribute("page", pageRender);
		modelo.addAttribute("filtro", filtro); // Para mantener el valor en el input
		modelo.addAttribute("disponibilidad", disponibilidad); // Para mantener el valor en el select

		return "admin/inmueble/MantInmueble"; // Nos retorna al MantInmueble.html
	}

	@GetMapping("/inmuebles/nuevo")
	public String mostrarFormularioDeRegistro(Model modelo) {
		Inmueble inmueble = new Inmueble();
		inmueble.setAdministrador(new Administrador());
		modelo.addAttribute("inmueble", inmueble);
		return "admin/inmueble/CrearInmueble";
	}

	@PostMapping("/inmuebles")
	public String guardar(@ModelAttribute("inmueble") Inmueble inmueble, @RequestParam("file") MultipartFile file) {
		// Subir imagen
		String url = clouService.SubirImagen(file);
		inmueble.setImagenHabitacion(url);

		// Obtener el correo del administrador autenticado
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String correo = auth.getName(); // Spring Security devuelve el username, que en este caso es el correo

		// Obtener administrador desde el servicio
		Administrador admin = admiService.findByCorreo(correo);
		inmueble.setAdministrador(admin); // Asignar al inmueble

		// Guardar inmueble
		inmuService.guardar(inmueble);

		return "redirect:/admin/inmueble/inmuebles";
	}

	@GetMapping("/inmuebles/detalle/{id}")
	public String verDetalleInmueble(@PathVariable int id, Model modelo) {
		modelo.addAttribute("inmueble", inmuService.obtenerPorId(id));
		return "admin/inmueble/DetalleInmueble";
	}

	@GetMapping("/inmuebles/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable int id, Model modelo) {
		modelo.addAttribute("inmueble", inmuService.obtenerPorId(id));
		return "admin/inmueble/EditarInmueble";
	}

	@PostMapping("/inmuebles/{id}")
	public String actualizar(@PathVariable int id, @ModelAttribute("inmueble") Inmueble inmueble,
			@RequestParam(value = "file", required = false) MultipartFile file, Model modelo) {
		Inmueble inmuebleExistente = inmuService.obtenerPorId(id);
		inmuebleExistente.setId(id);
		inmuebleExistente.setNombre(inmueble.getNombre());
		inmuebleExistente.setCapacidad(inmueble.getCapacidad());
		inmuebleExistente.setNumeroHabitaciones(inmueble.getNumeroHabitaciones());
		inmuebleExistente.setDescripcion(inmueble.getDescripcion());
		inmuebleExistente.setServiciosIncluidos(inmueble.getServiciosIncluidos());
		inmuebleExistente.setDisponibilidad(inmueble.getDisponibilidad());
		inmuebleExistente.setPrecioPorNoche(inmueble.getPrecioPorNoche());

		if (file != null && !file.isEmpty()) {
			// Solo intenta eliminar la imagen antigua si existe una URL de imagen actual y
			// no es nula/vacía
			if (inmuebleExistente.getImagenHabitacion() != null && !inmuebleExistente.getImagenHabitacion().isEmpty()) {
				// Eliminar la imagen anterior por URL
				clouService.eliminarImagenPorUrl(inmuebleExistente.getImagenHabitacion());
			}
			// Subir nueva imagen
			String url = clouService.SubirImagen(file);
			inmuebleExistente.setImagenHabitacion(url);
		}
		inmuebleExistente.setAdministrador(inmueble.getAdministrador());
		inmuService.actualizar(inmuebleExistente);
		return "redirect:/admin/inmueble/inmuebles";
	}

	@GetMapping("/inmuebles/{id}")
	public String eliminar(@PathVariable int id) {
		Inmueble inmueble = inmuService.obtenerPorId(id);
		if (inmueble != null) {
			// Verifica si hay una imagen en Cloudinary asociada
			if (inmueble.getImagenHabitacion() != null && !inmueble.getImagenHabitacion().isEmpty()) {
				// Elimina la imagen de Cloudinary
				clouService.eliminarImagenPorUrl(inmueble.getImagenHabitacion());
			}
			// Elimina el inmueble de la base de datos
			inmuService.eliminar(id);
		}
		return "redirect:/admin/inmueble/inmuebles";
	}
}