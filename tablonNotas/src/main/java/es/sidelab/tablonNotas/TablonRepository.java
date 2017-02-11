package es.sidelab.tablonNotas;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TablonRepository extends JpaRepository<Tablon, Long>{

	public Tablon findByUserNameAndPrivado(String userName, boolean privado);
	
}
