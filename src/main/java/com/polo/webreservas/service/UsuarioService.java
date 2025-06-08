package com.polo.webreservas.service;

import com.polo.webreservas.model.Rol;

public interface UsuarioService {

	void registrarUsuario(String correo, String clave, Rol rol);
}
