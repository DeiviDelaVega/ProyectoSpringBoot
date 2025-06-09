package com.polo.webreservas.service;

import java.util.List;
import com.polo.webreservas.model.Administrador;

public interface AdministradorService {
	public abstract List<Administrador> listarTodo();
	public Administrador obtenerPorId(int id);
}