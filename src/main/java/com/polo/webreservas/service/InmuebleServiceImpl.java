package com.polo.webreservas.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.polo.webreservas.model.Inmueble;
import com.polo.webreservas.repository.InmuebleRepository;

@Service
public class InmuebleServiceImpl implements InmuebleService {

	@Autowired
	private InmuebleRepository repositorio;
	
	@Override
	public List<Inmueble> listarTodo() {
		return repositorio.findAll();
	}
	
	@Override
	public Inmueble guardar(Inmueble inmueble){
	    return repositorio.save(inmueble);
	}

	@Override
	public Inmueble obtenerPorId(int id) {
		return repositorio.findById(id).get();
	}

	@Override
	public Inmueble actualizar(Inmueble inmueble) {
		return repositorio.save(inmueble);
	}

	@Override
	public void eliminar(int id) {
		repositorio.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Inmueble> listarTodoPaginacion(Pageable pageable) {
		return repositorio.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Inmueble> listarTodoConFiltro(String filtro, Pageable pageable) {
		if (filtro != null && !filtro.trim().isEmpty()) {
	        return repositorio.filtrarPorDescripcionOServicio(filtro, pageable);
	    }
		return repositorio.findAll(pageable);
	}

	@Override
	public Page<Inmueble> listarTodoConFiltroYDisponibilidad(String filtro, String disponibilidad, Pageable pageable) {
		if ((filtro == null || filtro.trim().isEmpty()) && (disponibilidad == null || disponibilidad.isEmpty())) {
	        return repositorio.findAll(pageable);
	    } else if (filtro != null && !filtro.trim().isEmpty() && disponibilidad != null && !disponibilidad.isEmpty()) {
	        return repositorio.findByFiltroAndDisponibilidad(filtro, disponibilidad, pageable);
	    } else if (filtro != null && !filtro.trim().isEmpty()) {
	        return repositorio.filtrarPorDescripcionOServicio(filtro, pageable);
	    } else {
	        return repositorio.findByDisponibilidad(disponibilidad, pageable);
	    }
	}
}