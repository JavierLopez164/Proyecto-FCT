package backend.JDA.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Comida;
import backend.JDA.modelo.ComidaPK;
import backend.JDA.modelo.Foto;

@Repository
public interface ComidaRepositorio extends JpaRepository<Comida, ComidaPK> {

	@Query("UPDATE Comida c SET c.description = ?2 WHERE c.comidaPK = ?1")
	public int actualizaDescripcion (ComidaPK id, String description);

	@Query("UPDATE Comida c SET c.price = ?2 WHERE c.comidaPK = ?1")
	public int actualizarPrecio (ComidaPK id, float precio);

	@Query("SELECT c FROM Comida c WHERE c.comidaPK.nRestaurante = ?1")
	public List<Comida> obtenerComidasDeUnRestaurante(String restaurante);

	@Query("SELECT c.comidaPK.nComida, COUNT(c) AS veces FROM Pedido p JOIN p.comidas c GROUP BY c.comidaPK.nComida ORDER BY veces DESC")
	List<Object[]> top5ComidasMasPedidas();

	@Query("SELECT c.comidaPK.nComida, COUNT(c) AS veces FROM Pedido p JOIN p.comidas c WHERE c.comidaPK.nRestaurante = ?1restaurante GROUP BY c.comidaPK.nComida ORDER BY veces DESC")
	List<Object[]> top5ComidasPorRestaurante( String restaurante);
	
	@Query("SELECT DISTINCT c.comidaPK.nRestaurante FROM Comida c ")
	List<String>obtenerTodosLosRestaurantes();
	@Query("SELECT c.foto FROM Comida c WHERE c.comidaPK.nRestaurante=?1 ")
	List<Foto>obtenerTodasLasFotosDeUnRestaurantes(String restaurante);
}