package com.polo.webreservas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polo.webreservas.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByCorreo(String correo);


}
