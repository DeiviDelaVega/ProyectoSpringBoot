package com.polo.webreservas.controller;

import com.polo.webreservas.model.Cliente;
import com.polo.webreservas.service.ClienteService;
import com.polo.webreservas.util.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/admin/cliente")
public class ClienteController {

    @Autowired
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

        String urlBase = "/admin/cliente";
        if (!filtro.isBlank()) {
            urlBase += "?filtro=" + UriUtils.encode(filtro, StandardCharsets.UTF_8);
        }

        PageRender<Cliente> pageRender = new PageRender<>(urlBase, pagina);

        model.addAttribute("clientes", pagina.getContent());
        model.addAttribute("page", pageRender);
        model.addAttribute("filtro", filtro);
        model.addAttribute("titulo", "Listado de Clientes");

        // Leer atributos flash correctamente
        Object actualizadoObj = model.asMap().get("actualizado");
        Object eliminadoObj = model.asMap().get("eliminado");

        if (actualizadoObj instanceof Boolean actualizado) {
            model.addAttribute("actualizado", actualizado);
        }
        if (eliminadoObj instanceof Boolean eliminado) {
            model.addAttribute("eliminado", eliminado);
        }

        return "admin/cliente/cliente";
    }


    @GetMapping("/detalle/{id}")
    public String verDetalle(@PathVariable Integer id, Model modelo) {
        modelo.addAttribute("cliente", servicio.obtenerClientePorId(id));
        return "admin/cliente/DetalleCliente";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Integer id, Model modelo) {
        modelo.addAttribute("cliente", servicio.obtenerClientePorId(id));
        return "admin/cliente/EditarCliente";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Integer id,
                             @ModelAttribute("cliente") Cliente cliente,
                             RedirectAttributes redirectAttributes) {
        try {
            Cliente existente = servicio.obtenerClientePorId(id);
            existente.setNombre(cliente.getNombre());
            existente.setApellido(cliente.getApellido());
            existente.setNroDocumento(cliente.getNroDocumento());
            existente.setDireccion(cliente.getDireccion());
            existente.setNumeroTelf(cliente.getNumeroTelf());
            existente.setCorreo(cliente.getCorreo());
            existente.setEstado(cliente.getEstado());

            servicio.actualizarCliente(existente);
            redirectAttributes.addFlashAttribute("actualizado", true);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("actualizado", false);
        }
        return "redirect:/admin/cliente";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            servicio.eliminarCliente(id);
            redirectAttributes.addFlashAttribute("eliminado", true);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("eliminado", false);
        }
        return "redirect:/admin/cliente";
    }


}
