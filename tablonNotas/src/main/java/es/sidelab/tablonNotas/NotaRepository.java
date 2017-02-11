package es.sidelab.tablonNotas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<Nota, Long>{
	public List<Nota> findByTablon(Tablon tablon);
}
