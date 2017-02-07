package es.sidelab.tablonNotas;


import org.springframework.data.jpa.repository.JpaRepository;
import es.sidelab.tablonNotas.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
