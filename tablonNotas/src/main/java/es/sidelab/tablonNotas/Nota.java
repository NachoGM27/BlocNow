package es.sidelab.tablonNotas;

public class Nota {

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

	@Override
	public String toString() {
		return contenido;
	}

}
