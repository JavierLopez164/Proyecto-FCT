package backend.JDA.repositorios;

import org.springframework.stereotype.Repository;
import backend.JDA.modelo.Pedido;
import backend.JDA.modelo.dto.TopComidaDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, String> {

    @Query("SELECT p FROM Pedido p WHERE p.fechaCreacion BETWEEN ?1 AND ?2")
    List<Pedido> buscarPedidosEntreFechas(LocalDate desde, LocalDate hasta);

    List<Pedido> findByActivoTrue();
    
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
    
}

