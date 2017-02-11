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
	
	private String userName;
	private boolean privado;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="tablon")
	private List<Nota> notas = new ArrayList<Nota>();
	
	public Tablon(){ }
	public Tablon(String userName, boolean privado){this.userName = userName; this.privado = privado; }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	public boolean getPrivado(){
		return privado;
	}
	
	public void setPrivado(boolean privado){
		this.privado = privado;
	}

	public List<Nota> getNotas() {
		return notas;
	}
	
	public void setNotas(List<Nota> notas){
		this.notas = notas;
	}
	
}
