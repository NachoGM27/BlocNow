package es.sidelab.tablonNotas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TablonRepository extends JpaRepository<Tablon, Long>{

	public List<Tablon> findByUserNameAndPrivado(String userName, boolean privado);
	
}
