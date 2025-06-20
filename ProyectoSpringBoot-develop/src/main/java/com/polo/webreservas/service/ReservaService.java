package com.polo.webreservas.service;

import com.polo.webreservas.model.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ReservaService {

    Page<Reserva> BuscarPorFechas(LocalDate fechaInicio, LocalDate fechaFin, Pageable pageable);
    Page<Reserva> listarTodas(Pageable pageable);

}
