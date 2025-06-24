package com.polo.webreservas.service;

import com.polo.webreservas.model.Pago;
import com.polo.webreservas.model.Reserva;

import java.util.List;

public interface PagoService {
    void guardar(Pago pago);
    List<Pago> listarTodos();
    Pago buscarPorId(Integer id);
    void eliminar(Pago pago);
    Pago buscarPorReserva(Reserva reserva);

}
