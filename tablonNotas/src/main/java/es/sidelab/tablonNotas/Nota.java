package es.sidelab.tablonNotas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Nota {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	
	private String nombre;
	private String contenido;

	public Nota() {

	}

	public Nota(String contenido) {
		this.contenido = contenido;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return contenido;
	}

}

