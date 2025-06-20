package com.polo.webreservas.service;

import java.util.List;
import com.polo.webreservas.model.Inmueble;

public interface InmuebleService {
    List<Inmueble> listarTodos();
    Inmueble guardar(Inmueble inmueble);
    Inmueble buscarPorId(Integer id);
    void eliminarPorId(Integer id);
    Inmueble actualizar(Inmueble inmueble);
}
