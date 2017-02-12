package es.sidelab.tablonNotas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Mensaje {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	
	private String contenido;

	@ManyToOne
	private Usuario emisor;
	@ManyToOne
	private Usuario receptor;
	
	private boolean isMio;
	
	public Mensaje(){}
	public Mensaje(String contenido) {this.contenido = contenido;}
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public String getContenido(){
		return contenido;
	}
	
	public void setContenido(String contenido){
		this.contenido = contenido;
	}
	
	public Usuario getEmisor(){
		return emisor;
	}
	
	public void setEmisor(Usuario emisor){
		this.emisor = emisor;
	}
	
	public Usuario getReceptor(){
		return receptor;
	}
	
	public void setReceptor(Usuario receptor){
		this.receptor = receptor;
	}
	
	public boolean getIsMio(){
		return isMio;
	}
	
	public void setIsMio(boolean isMio){
		this.isMio = isMio;
	}
}
