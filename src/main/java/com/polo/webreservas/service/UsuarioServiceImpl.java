package com.polo.webreservas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.polo.webreservas.model.Rol;
import com.polo.webreservas.model.Usuario;
import com.polo.webreservas.repository.UsuarioRepository;


@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired 
	PasswordEncoder passwordEncoder;

	@Override
	public void registrarUsuario(String correo, String clave, Rol rol) {
		Usuario usuario = new Usuario();

		usuario.setCorreo(correo);
		usuario.setClave(passwordEncoder.encode(clave));
		usuario.setRol(rol);
		usuarioRepository.save(usuario);
	}
}
