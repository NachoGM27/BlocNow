package es.sidelab.tablonNotas;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
	Usuario findByName(String name);	 
}
