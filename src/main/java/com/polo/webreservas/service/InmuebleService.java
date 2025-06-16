package com.polo.webreservas.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.polo.webreservas.model.Inmueble;

public interface InmuebleService {
	public List<Inmueble> listarTodo();
	public Page<Inmueble> listarTodoPaginacion(Pageable pageable);
	public Page<Inmueble> listarTodoConFiltro(String filtro, Pageable pageable);
	public Page<Inmueble> listarTodoConFiltroYDisponibilidad(String filtro, String disponibilidad, Pageable pageable);
	public Inmueble guardar(Inmueble inmueble);
	public Inmueble obtenerPorId(int id);
	public Inmueble actualizar(Inmueble inmueble);
	public void eliminar(int id);
}