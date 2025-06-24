package com.polo.webreservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.polo.webreservas.model.Pago;
import com.polo.webreservas.model.Reserva;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
	Pago findByReserva(Reserva reserva);

}
