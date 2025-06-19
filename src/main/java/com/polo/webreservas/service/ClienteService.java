package com.polo.webreservas.service;

import com.polo.webreservas.model.Cliente;

public interface ClienteService {
	
	void registrarCliente(Cliente cliente, String clave);

	public Cliente findByCorreo(String correo);

}
