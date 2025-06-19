package com.polo.webreservas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "Inmueble")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inmueble {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Inmueble")
    private Integer id;

    @NotNull
    @Size(max = 100)
    @Column(name = "Nombre")
    private String nombre;

    @NotNull
    @Min(1)
    @Column(name = "Capacidad")
    private Integer capacidad;

    @NotNull
    @Min(1)
    @Column(name = "Numero_Habitaciones")
    private Integer numeroHabitaciones;

    @Size(max = 300)
    @Column(name = "Descripcion")
    private String descripcion;

    @Size(max = 200)
    @Column(name = "Servicios_Incluidos")
    private String serviciosIncluidos;

    @NotNull
    @Pattern(regexp = "Si|No")
    @Column(name = "Disponibilidad")
    private String disponibilidad;

    @NotNull
    @DecimalMin(value = "0.01")
    @Column(name = "Precio_Por_Noche")
    private BigDecimal precioPorNoche;

    @Size(max = 100)
    @Column(name = "Imagen_Habitacion")
    private String imagenHabitacion;

	public Inmueble() {
		super();
	}

	public Inmueble(Integer id, @NotNull @Size(max = 100) String nombre, @NotNull @Min(1) Integer capacidad,
			@NotNull @Min(1) Integer numeroHabitaciones, @Size(max = 300) String descripcion,
			@Size(max = 200) String serviciosIncluidos, @NotNull @Pattern(regexp = "Si|No") String disponibilidad,
			@NotNull @DecimalMin("0.01") BigDecimal precioPorNoche, @Size(max = 100) String imagenHabitacion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.numeroHabitaciones = numeroHabitaciones;
		this.descripcion = descripcion;
		this.serviciosIncluidos = serviciosIncluidos;
		this.disponibilidad = disponibilidad;
		this.precioPorNoche = precioPorNoche;
		this.imagenHabitacion = imagenHabitacion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public Integer getNumeroHabitaciones() {
		return numeroHabitaciones;
	}

	public void setNumeroHabitaciones(Integer numeroHabitaciones) {
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


    
    
}
