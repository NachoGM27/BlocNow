package es.sidelab.tablonNotas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	public List<Usuario> findByName(String name);
}
