package com.polo.webreservas.controller;

import com.polo.webreservas.model.Cliente;
import com.polo.webreservas.service.ClienteService;
import com.polo.webreservas.util.PageRender;

import java.nio.charset.StandardCharsets;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;


@Controller
@RequestMapping("/admin/cliente")
public class ClienteController {

    private final ClienteService servicio;

    public ClienteController(ClienteService servicio) {
        this.servicio = servicio;
    }

    @GetMapping({"", "/"})
    public String listarClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(name = "filtro", defaultValue = "") String filtro,
            Model model) {

        PageRequest pageable = PageRequest.of(page, 5);
        Page<Cliente> pagina = filtro.isBlank()
                ? servicio.listarTodoPaginacion(pageable)
                : servicio.listarTodoConFiltro(filtro, pageable);

        /*Construimos la URL base que PageRender necesita */
        String urlBase = "/admin/cliente";
        if (!filtro.isBlank()) {                    // agrega ?filtro=... solo si hay texto
            urlBase += "?filtro=" + UriUtils.encode(filtro, StandardCharsets.UTF_8);
        }

        /*Creamos tu PageRender con esa URL */
        PageRender<Cliente> pageRender = new PageRender<>(urlBase, pagina);

        /* ③  Atributos que usa la vista */
        model.addAttribute("clientes", pagina.getContent());
        model.addAttribute("page",     pageRender);   // ← nombre idéntico al fragmento
        model.addAttribute("filtro",   filtro);
        model.addAttribute("titulo",   "Listado de Clientes");

        return "admin/cliente/cliente";
    }


    @GetMapping("/detalle/{id}")
    public String verDetalle(@PathVariable Integer id, Model modelo) {
        modelo.addAttribute("cliente", servicio.obtenerClientePorId(id));
        return "admin/cliente/DetalleCliente";   // DetalleCliente.html
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Integer id, Model modelo) {
        modelo.addAttribute("cliente", servicio.obtenerClientePorId(id));
        return "admin/cliente/EditarCliente";    // EditarCliente.html
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Integer id,
                             @ModelAttribute("cliente") Cliente cliente) {

        Cliente existente = servicio.obtenerClientePorId(id);
        existente.setNombre(cliente.getNombre());
        existente.setApellido(cliente.getApellido());
        existente.setNroDocumento(cliente.getNroDocumento());
        existente.setDireccion(cliente.getDireccion());
        existente.setNumeroTelf(cliente.getNumeroTelf());
        existente.setCorreo(cliente.getCorreo());

        servicio.actualizarCliente(existente);

        return "redirect:/admin/cliente";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Integer id) {
        servicio.eliminarCliente(id);
        return "redirect:/admin/cliente";
    }
}
