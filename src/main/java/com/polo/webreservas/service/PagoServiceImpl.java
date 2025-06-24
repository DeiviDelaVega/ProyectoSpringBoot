package com.polo.webreservas.service;

import com.polo.webreservas.model.Pago;
import com.polo.webreservas.model.Reserva;
import com.polo.webreservas.repository.PagoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Override
    public void guardar(Pago pago) {
        pagoRepository.save(pago);
    }

    @Override
    public List<Pago> listarTodos() {
        return pagoRepository.findAll();
    }

    @Override
    public Pago buscarPorId(Integer id) {
        return pagoRepository.findById(id).orElse(null);
    }
    
    @Override
    public void eliminar(Pago pago) {
        pagoRepository.delete(pago);
    }

    @Override
    public Pago buscarPorReserva(Reserva reserva) {
        return pagoRepository.findByReserva(reserva);
    }

}
