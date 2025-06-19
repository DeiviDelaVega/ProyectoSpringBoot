package com.polo.webreservas.repository;

import com.polo.webreservas.model.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    Page<Reserva> findByEstadoReserva(String estado, Pageable pageable);
    Page<Reserva> findByFechaInicioBetween(LocalDate fechaInicio, LocalDate fechaFin, Pageable pageable);
}
