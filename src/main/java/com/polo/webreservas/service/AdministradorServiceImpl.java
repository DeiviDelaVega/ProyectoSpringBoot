package com.polo.webreservas.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.polo.webreservas.model.Administrador;
import com.polo.webreservas.repository.AdministradorRepository;

@Service
public class AdministradorServiceImpl implements AdministradorService{
	@Autowired
	private AdministradorRepository repositorio;

	@Override
	public List<Administrador> listarTodo() {
		return repositorio.findAll();
	}

	@Override
	public Administrador obtenerPorId(int id) {
		return repositorio.findById(id).get();
	}
}