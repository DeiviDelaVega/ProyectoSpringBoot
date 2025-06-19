package com.polo.webreservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polo.webreservas.model.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Integer>{
	
	public Administrador findByCorreo(String correo);

}
