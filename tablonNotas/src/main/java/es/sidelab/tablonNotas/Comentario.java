package es.sidelab.tablonNotas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comentario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String usuario;
	private String contenido;
	
	@ManyToOne
	private Nota nota;

	protected Comentario() {
	}

	public Comentario(String usuario, String contenido) {
		this.usuario = usuario;
		this.contenido = contenido;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	public Nota getNota(){
		return nota;
	}
	
	public void setNota(Nota nota){
		this.nota = nota;
	}
	
	@Override
	public String toString() {
		return usuario + contenido;
	}

}
