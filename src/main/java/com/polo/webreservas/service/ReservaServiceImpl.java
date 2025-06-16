package com.polo.webreservas.service;

import com.polo.webreservas.model.Reserva;
import com.polo.webreservas.repository.ReservaRepository;
import com.polo.webreservas.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public Page<Reserva> listarTodas(Pageable pageable) {
        return reservaRepository.findAll(pageable);
    }

    @Override
    public Page<Reserva> filtrarPorEstado(String estado, Pageable pageable) {
        return reservaRepository.findByEstadoReserva(estado, pageable);
    }

    @Override
    public Page<Reserva> filtrarPorFechas(LocalDate fechaInicio, LocalDate fechaFin, Pageable pageable) {
        return reservaRepository.findByFechaInicioBetween(fechaInicio, fechaFin, pageable);
    }

    @Override
    public Optional<Reserva> obtenerPorId(Long id) {
        return reservaRepository.findById(id);
    }

    @Override
    public void actualizarEstado(Long id, String nuevoEstado) {
        Optional<Reserva> opt = reservaRepository.findById(id);
        if (opt.isPresent()) {
            Reserva reserva = opt.get();
            reserva.setEstadoReserva(nuevoEstado);
            reservaRepository.save(reserva);
        }
    }

    @Override
    public void eliminar(Long id) {
        reservaRepository.deleteById(id);
    }
}
