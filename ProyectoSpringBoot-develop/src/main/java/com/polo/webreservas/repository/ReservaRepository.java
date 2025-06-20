package com.polo.webreservas.repository;

import com.polo.webreservas.model.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    @Query("SELECT r FROM Reserva r WHERE r.fechaInicio >= :fechaInicio AND r.fechaFin <= :fechaFin")
    Page<Reserva> BuscarPorFechas(@Param("fechaInicio") LocalDate fechaInicio,
                                  @Param("fechaFin") LocalDate fechaFin,
                                  Pageable pageable);

}
