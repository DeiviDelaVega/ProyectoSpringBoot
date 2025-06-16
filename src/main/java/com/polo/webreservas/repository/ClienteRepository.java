package com.polo.webreservas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.polo.webreservas.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	Cliente findByCorreo(String correo);

	@Query("SELECT c FROM   Cliente c WHERE  LOWER(c.apellido) LIKE LOWER(CONCAT('%', :filtro, '%'))"
			+ "OR  c.nroDocumento  LIKE CONCAT('%', :filtro, '%')")
	Page<Cliente> filtrarPorApellidoONroDocumento(String filtro, Pageable pageable);
}
