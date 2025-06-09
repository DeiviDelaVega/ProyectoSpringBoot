package com.polo.webreservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.polo.webreservas.model.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Integer>{

}