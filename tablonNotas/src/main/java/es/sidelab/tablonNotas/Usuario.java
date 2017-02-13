package es.sidelab.tablonNotas;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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

	private String name = "";
	private String password = "";
	
	@OneToOne(cascade = CascadeType.ALL)
	private Tablon tablonPrivado;
	@OneToOne(cascade = CascadeType.ALL)
	private Tablon tablonPublico;
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Usuario> amigos;
	@OneToMany(cascade = CascadeType.ALL, mappedBy="emisor")
	private List<Mensaje> mensajes;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	public List<Usuario> getAmigos(){
		return amigos;
	}
	
	public void setAmigos(List<Usuario> amigos){
		this.amigos = amigos;
	}
	
	public List<Mensaje> getMensajes(){
		return mensajes;
	}
	
	public void setMensajes(List<Mensaje> mensajes){
		this.mensajes = mensajes;
	}
	
}