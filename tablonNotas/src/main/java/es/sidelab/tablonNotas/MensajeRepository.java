package es.sidelab.tablonNotas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MensajeRepository extends JpaRepository<Mensaje, Long>{
	public List<Mensaje> findByEmisorAndReceptor(Usuario emisor, Usuario receptor);
}