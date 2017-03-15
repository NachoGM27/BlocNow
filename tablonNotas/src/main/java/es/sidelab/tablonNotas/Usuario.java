package es.sidelab.tablonNotas;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	private String passwordHash = "";
	private String email = "";
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Tablon tablonPrivado;
	@OneToOne(cascade = CascadeType.ALL)
	private Tablon tablonPublico;
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Usuario> amigos;
	@OneToMany(cascade = CascadeType.ALL, mappedBy="emisor")
	private List<Mensaje> mensajes;
	
	public Usuario(){
	
	}
	
	public Usuario(String name, String passwordHash, String email){
		this.name = name;
		this.passwordHash = passwordHash;
		this.email = email;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public List<String> getRoles(){
		return roles;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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