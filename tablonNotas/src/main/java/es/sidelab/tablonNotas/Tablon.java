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
public class Tablon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Nota> notas = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Nota> getNotas() {
		return notas;
	}
	
	public void setNotas(List<Nota> notas){
		this.notas = notas;
	}
	
}
