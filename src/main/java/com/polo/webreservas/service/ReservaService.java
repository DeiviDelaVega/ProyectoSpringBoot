package com.polo.webreservas.service;

import com.polo.webreservas.model.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface ReservaService {
    Page<Reserva> listarTodas(Pageable pageable);
    Page<Reserva> filtrarPorEstado(String estado, Pageable pageable);
    Page<Reserva> filtrarPorFechas(LocalDate fechaInicio, LocalDate fechaFin, Pageable pageable);
    Optional<Reserva> obtenerPorId(Long id);
    void actualizarEstado(Long id, String nuevoEstado);
    void eliminar(Long id);
}
