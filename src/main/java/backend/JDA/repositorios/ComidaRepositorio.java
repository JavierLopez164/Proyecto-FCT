package backend.JDA.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Comida;
import backend.JDA.modelo.ComidaPK;

@Repository
public interface ComidaRepositorio extends CrudRepository<Comida, ComidaPK> {
	
	@Query("UPDATE Comida c SET c.descripcion = ?2 WHERE c.comidaPK = ?1")
	public int actualizaDescripcion (ComidaPK id, String descripcion);
	
	@Query("UPDATE Comida c SET c.precio = ?2 WHERE c.comidaPK = ?1")
	public int actualizarPrecio (ComidaPK id, float precio);
	
	@Query("UPDATE Comida c SET c.valoracion = ?2 WHERE c.comidaPK = ?1")
	public int actualizarValoracion (ComidaPK id, int valoracion);
	
	@Query("SELECT c FROM Comida c WHERE c.comidaPK.nRestaurante = ?1")
	public List<Comida> obtenerComidasDeUnRestaurante(String restaurante);
	
}
