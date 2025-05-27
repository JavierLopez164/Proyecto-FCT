package backend.JDA.repositorios;

import java.util.List;

import backend.JDA.modelo.dto.TopComidaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Comida;
import backend.JDA.modelo.ComidaPK;
import backend.JDA.modelo.Foto;

@Repository
public interface ComidaRepositorio extends JpaRepository<Comida, ComidaPK> {

	@Query("UPDATE Comida c SET c.description = ?2 WHERE c.comidaPK = ?1")
	 int actualizaDescripcion (ComidaPK id, String description);

	@Query("UPDATE Comida c SET c.price = ?2 WHERE c.comidaPK = ?1")
	 int actualizarPrecio (ComidaPK id, float precio);

	@Query("SELECT c FROM Comida c WHERE c.comidaPK.nRestaurante = ?1")

	 List<Comida> obtenerComidasDeUnRestaurante(String restaurante);
	@Query("SELECT new backend.JDA.modelo.dto.TopComidaDTO(c.comida.comidaPK.nComida, c.comida.comidaPK.nRestaurante, SUM(c.cantidad)) " +
			"FROM Pedido p JOIN p.items c " +
			"GROUP BY c.comida.comidaPK.nComida, c.comida.comidaPK.nRestaurante " +
			"ORDER BY SUM(c.cantidad) DESC")
	List<TopComidaDTO> top5ComidasMasPedidas();


	@Query("SELECT new backend.JDA.modelo.dto.TopComidaDTO(c.comida.comidaPK.nComida, c.comida.comidaPK.nRestaurante, SUM(c.cantidad)) " +
			"FROM Pedido p JOIN p.items c " +
			"WHERE p.restaurante = ?1 " +
			"GROUP BY c.comida.comidaPK.nComida, c.comida.comidaPK.nRestaurante " +
			"ORDER BY SUM(c.cantidad) DESC")
	List<TopComidaDTO> top5ComidasPorRestaurante(String restaurante);
	
	@Query("SELECT DISTINCT c.comidaPK.nRestaurante FROM Comida c ")
	List<String>obtenerTodosLosRestaurantes();
	@Query("SELECT c FROM Comida c WHERE c.comidaPK.nRestaurante = ?1 ")
	List<Comida>obtenerTodasLasComidasDeUnRestaurantes(String restaurante);
}