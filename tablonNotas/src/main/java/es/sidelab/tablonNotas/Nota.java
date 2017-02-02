package es.sidelab.tablonNotas;

public class Nota {

	
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

