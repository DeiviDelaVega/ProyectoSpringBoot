package com.polo.webreservas.service;

import java.util.List;
import com.polo.webreservas.model.Administrador;

public interface AdminService {
	void registrarAdmin(Administrador admin, String clave);
	public abstract List<Administrador> listarTodo();
	public Administrador obtenerPorId(int id);
	public Administrador findByCorreo(String correo);
}