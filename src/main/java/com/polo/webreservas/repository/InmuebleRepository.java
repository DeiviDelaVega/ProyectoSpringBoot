package com.polo.webreservas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.polo.webreservas.model.Inmueble;

@Repository
public interface InmuebleRepository extends JpaRepository<Inmueble, Integer>, JpaSpecificationExecutor<Inmueble>{
	@Query("SELECT i FROM Inmueble i WHERE (i.descripcion LIKE %?1% OR i.serviciosIncluidos LIKE %?1%) AND i.disponibilidad = ?2")
	Page<Inmueble> findByFiltroAndDisponibilidad(String filtro, String disponibilidad, Pageable pageable);

	@Query("SELECT i FROM Inmueble i WHERE i.descripcion LIKE %?1% OR i.serviciosIncluidos LIKE %?1%")
	Page<Inmueble> filtrarPorDescripcionOServicio(String filtro, Pageable pageable);

	Page<Inmueble> findByDisponibilidad(String disponibilidad, Pageable pageable);

}