package backend.JDA.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Foto;

@Repository
public interface FotoRepositorio extends JpaRepository<Foto, Integer> {
	@Query("SELECT f FROM Foto f ORDER BY f.fecha DESC")
	List<Foto> ordenarFechaAntiguo();
	@Query("SELECT f FROM Foto f ORDER BY f.fecha ASC")
	List<Foto> ordenarFechaActual();
	
}
