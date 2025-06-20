package com.polo.webreservas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polo.webreservas.model.Inmueble;
import com.polo.webreservas.repository.InmuebleRepository;

@Service
public class InmuebleServiceImpl implements InmuebleService {

    @Autowired
    private InmuebleRepository inmuebleRepository;

    @Override
    public List<Inmueble> listarTodos() {
        return inmuebleRepository.findAll();
    }

    @Override
    public Inmueble guardar(Inmueble inmueble) {
        return inmuebleRepository.save(inmueble);
    }

    @Override
    public Inmueble buscarPorId(Integer id) {
        return inmuebleRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarPorId(Integer id) {
        inmuebleRepository.deleteById(id);
    }

    @Override
    public Inmueble actualizar(Inmueble inmueble) {
        return inmuebleRepository.save(inmueble);
    }
}
