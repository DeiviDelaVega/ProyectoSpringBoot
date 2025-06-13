package com.polo.webreservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.polo.webreservas.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	Cliente findByCorreo(String correo);
}
