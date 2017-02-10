package es.sidelab.tablonNotas;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Nota {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	
	private String contenido;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Comentario> comentarios = new ArrayList<>();

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
		
	public List<Comentario> getComentario() {
		return comentarios;
	}
	
	public void setComentario(List<Comentario> comentarios){
		this.comentarios = comentarios;
	}
	
	@Override
	public String toString() {
		return contenido;
	}
	
}

