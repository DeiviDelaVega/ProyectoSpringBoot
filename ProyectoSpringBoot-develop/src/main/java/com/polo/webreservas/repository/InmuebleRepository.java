package com.polo.webreservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.polo.webreservas.model.Inmueble;

public interface InmuebleRepository extends JpaRepository<Inmueble, Integer> {
}
