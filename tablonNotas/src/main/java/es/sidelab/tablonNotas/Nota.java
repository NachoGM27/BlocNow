package es.sidelab.tablonNotas;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Nota {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	
	private String contenido;
	
	@ManyToOne
	private Tablon tablon;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="nota")
	private List<Comentario> comentarios = new ArrayList<Comentario>();

	public Nota() {

	}

	public Nota(String contenido) {
		this.contenido = contenido;
	}
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
		
	public List<Comentario> getComentarios() {
		return comentarios;
	}
	
	public void setComentarios(List<Comentario> comentarios){
		this.comentarios = comentarios;
	}
	
	public Tablon getTablon(){
		return tablon;
	}
	
	public void setTablon(Tablon tablon){
		this.tablon = tablon;
	}
	
	@Override
	public String toString() {
		return contenido;
	}
	
}

