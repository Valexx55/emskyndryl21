package edu.kyndryl.academy.msalumnosprofe.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/*
/**
 *  comentario de código
 * @author Val
 * @since 1.0
 */

/**
 * esta clase, representa a un alumno con sus datos
 * DTO- Data Transfer/Type Object - Bean - POJO 
 * Java Bean (Plain Old Java Object) - atributos, métodos de acceso y constructor por defecto
 * Spring Bean - clase que gestiona / instancia Spring automáticamente
 * DTO - DAO AlumnoDTO - BEAN clase pelada
 * Data Access Object - AlumnoDAO - ALumno en su relación con la base de datos
 * JPA - Entity	
 * 1) decimos a Spring que esto es una tabla -- Identificamos como Entity
 * 
 */

@Entity //le digo a Spring que esta clase está asociada a una tabla
@Table(name = "alumnos") //el nombre de la tabla en bd a la que está asociada esta clase
public class Alumno {
	
	//comentario de línea
	/*
	 * comentario de bloque
	 */
	@Id //le digo que este atributo es la PK 
	@GeneratedValue(strategy = GenerationType.IDENTITY) //configuramos el autoincremento MYSQL
	private Long id;//clave primaria
	
	@Size(min = 3, max = 20)
	private String nombre;
	
	@Min(10)
	@Max(100)
	private int edad;
	
	@NotEmpty //longitud mayor que uno
	private String apellido;
	
	@Email
	private String email;
	
	@JsonIgnore //evitando serializar este atributo a JSON
	private LocalDateTime creadoEn;
	
	@PrePersist //esta anotación hace que Spring llame a este método antes de insertar un nuevo alumno
	private void generarFechaCreacion ()
	{
		this.creadoEn = LocalDateTime.now(); //obtengo la fecha actual y se la asigno al alumno
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreadoEn() {
		return creadoEn;
	}

	public void setCreadoEn(LocalDateTime creadoEn) {
		this.creadoEn = creadoEn;
	}

	//constructor por defecto
	public Alumno() {
		// TODO Auto-generated constructor stub
	}

	//constructor canónico
	public Alumno(String nombre, int edad, String apellido, String email) {
		super();
		this.nombre = nombre;
		this.edad = edad;
		this.apellido = apellido;
		this.email = email;
	}
	
	public Alumno(Long id, String nombre, int edad, String apellido, String email, LocalDateTime creadoEn) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.edad = edad;
		this.apellido = apellido;
		this.email = email;
		this.creadoEn = creadoEn;
	}

	//métodos de acceso
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getEdad() {
		return edad;
	}
	
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Alumno [id=" + id + ", nombre=" + this.nombre + ", edad=" + edad + ", apellido=" + apellido + ", email="
				+ email + ", creadoEn=" + creadoEn + "]";
	}
	
	
	
	
	

}
