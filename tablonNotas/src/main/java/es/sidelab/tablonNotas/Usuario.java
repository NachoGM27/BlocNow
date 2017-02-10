package es.sidelab.tablonNotas;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import es.sidelab.tablonNotas.Usuario;

@Entity
@Component
@SessionScope
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	
	private String nombre = "";
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Nota> notas = new ArrayList<>();
	
	//relacion 1:2 mirar como ponerlo
	private Tablon tablonPrivado;
	private Tablon tablonPublico;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Tablon getTablonPrivado() {
		return tablonPrivado;
	}

	public void setTablonPrivado(Tablon tablonPrivado) {
		this.tablonPrivado = tablonPrivado;
	}
	
	public Tablon getTablonPublico() {
		return tablonPublico;
	}

	public void setTablonPublico(Tablon tablonPublico) {
		this.tablonPublico = tablonPublico;
	}

	public List<Nota> getNotas() {
		return notas;
	}
	
	public void setNotas(List<Nota> notas){
		this.notas = notas;
	}
	
}