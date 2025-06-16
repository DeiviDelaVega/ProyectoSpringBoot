package com.polo.webreservas.model;

import java.math.BigDecimal;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Inmueble")
public class Inmueble {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Inmueble")
    private int id;

    @NotNull
    @Size(max = 100)
    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;

    @NotNull
    @Positive
    @Column(name = "Capacidad", nullable = false)
    private int capacidad;

    @NotNull
    @Positive
    @Column(name = "Numero_Habitaciones", nullable = false)
    private int numeroHabitaciones;

    @Size(max = 300)
    @Column(name = "Descripcion", length = 300)
    private String descripcion;

    @Size(max = 200)
    @Column(name = "Servicios_Incluidos", length = 200)
    private String serviciosIncluidos;

    @Column(name = "Disponibilidad", length = 2)
    private String disponibilidad;

    @Positive
    @Column(name = "Precio_Por_Noche", precision = 10, scale = 2)
    private BigDecimal precioPorNoche;

    @Size(max = 300)
    @Column(name = "Imagen_Habitacion", length = 300)
    private String imagenHabitacion;

    @NotNull(message = "Debe seleccionar un administrador")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Administrador")
    private Administrador administrador;
    
	public Inmueble(@NotNull @Size(max = 100) String nombre, @NotNull @Positive int capacidad,
			@NotNull @Positive int numeroHabitaciones, @Size(max = 300) String descripcion,
			@Size(max = 200) String serviciosIncluidos, String disponibilidad, @Positive BigDecimal precioPorNoche,
			@Size(max = 300) String imagenHabitacion,
			@NotNull(message = "Debe seleccionar un administrador") Administrador administrador) {
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.numeroHabitaciones = numeroHabitaciones;
		this.descripcion = descripcion;
		this.serviciosIncluidos = serviciosIncluidos;
		this.disponibilidad = disponibilidad;
		this.precioPorNoche = precioPorNoche;
		this.imagenHabitacion = imagenHabitacion;
		this.administrador = administrador;
	}
	
	public Inmueble() {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public int getNumeroHabitaciones() {
		return numeroHabitaciones;
	}

	public void setNumeroHabitaciones(int numeroHabitaciones) {
		this.numeroHabitaciones = numeroHabitaciones;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getServiciosIncluidos() {
		return serviciosIncluidos;
	}

	public void setServiciosIncluidos(String serviciosIncluidos) {
		this.serviciosIncluidos = serviciosIncluidos;
	}

	public String getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(String disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public BigDecimal getPrecioPorNoche() {
		return precioPorNoche;
	}

	public void setPrecioPorNoche(BigDecimal precioPorNoche) {
		this.precioPorNoche = precioPorNoche;
	}

	public String getImagenHabitacion() {
		return imagenHabitacion;
	}

	public void setImagenHabitacion(String imagenHabitacion) {
		this.imagenHabitacion = imagenHabitacion;
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}
}