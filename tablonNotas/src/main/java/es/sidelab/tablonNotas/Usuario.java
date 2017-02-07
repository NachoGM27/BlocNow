package es.sidelab.tablonNotas;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Entity
@Component
@SessionScope
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	
	private String nombre = "";
	private List<Nota> notas = new ArrayList<>();

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Nota> getNotas() {
		return notas;
	}
}