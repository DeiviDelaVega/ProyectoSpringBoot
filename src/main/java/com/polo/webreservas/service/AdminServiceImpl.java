package com.polo.webreservas.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.polo.webreservas.model.Administrador;
import com.polo.webreservas.model.Rol;
import com.polo.webreservas.repository.AdministradorRepository;
import com.polo.webreservas.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class AdminServiceImpl implements AdminService {
	private final UsuarioRepository usuarioRepository;
    private final AdministradorRepository administradorRepository;
    private final UsuarioService usuarioService;

    public AdminServiceImpl(AdministradorRepository administradorRepository,
                            UsuarioRepository usuarioRepository,
                            UsuarioService usuarioService) {
        this.administradorRepository = administradorRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    @Transactional
    public void registrarAdmin(Administrador admin, String clave) {
        administradorRepository.save(admin);
        usuarioService.registrarUsuario(admin.getCorreo(), clave, Rol.admin);
    }
    
    @Override
	public List<Administrador> listarTodo() {
		return administradorRepository.findAll();
	}

	@Override
	public Administrador obtenerPorId(int id) {
		return administradorRepository.findById(id).get();
	}

	@Override
	public Administrador findByCorreo(String correo) {
		return administradorRepository.findByCorreo(correo);
	}
}
