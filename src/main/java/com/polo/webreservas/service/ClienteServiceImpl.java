package com.polo.webreservas.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.polo.webreservas.model.Cliente;
import com.polo.webreservas.model.Rol;
import com.polo.webreservas.repository.ClienteRepository;
import com.polo.webreservas.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor; 

@Service
public class ClienteServiceImpl implements ClienteService {
	private ClienteRepository clienteRepository;
	private UsuarioService usuarioService;
	private final EmailService emailService;

	public ClienteServiceImpl(ClienteRepository clienteRepo, UsuarioRepository usuarioRepo,
			UsuarioService usuarioService, EmailService emailService) {
		this.clienteRepository = clienteRepo;
		this.usuarioService = usuarioService;
		this.emailService = emailService;
	}

	@Override
	@Transactional
	public void registrarCliente(Cliente cliente, String clave) {
		cliente.setFechaRegistro(LocalDateTime.now());
		clienteRepository.save(cliente);
		usuarioService.registrarUsuario(cliente.getCorreo(), clave, Rol.cliente);

		String asunto = "Bienvenido a Polo Web Reservas";
		String cuerpo = "Hola " + cliente.getNombre() + ", gracias por registrarte con nosotros.";
		emailService.sendEmail(cliente.getCorreo(), asunto, cuerpo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> listarTodosLosClientes() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente findByCorreo(String correo) {
	    return clienteRepository.findByCorreo(correo);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente obtenerClientePorId(Integer id) {
		return clienteRepository.findById(id).get();
	}

	@Override
	public Cliente actualizarCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@Override
	public void eliminarCliente(Integer id) {
		clienteRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> listarTodoPaginacion(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}

	@Override
    @Transactional(readOnly = true)
    public Page<Cliente> listarTodoConFiltro(String filtro, Pageable pageable) {
        if (filtro == null || filtro.trim().isEmpty()) {
            return clienteRepository.findAll(pageable);
        }
        return clienteRepository.filtrarPorApellidoONroDocumento(filtro.trim(), pageable);
    }
}