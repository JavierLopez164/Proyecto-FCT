package backend.JDA.repositorios;

import org.springframework.stereotype.Repository;
import backend.JDA.modelo.Pedido;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface PedidoRepositorio extends JpaRepository<Pedido, String> {

    @Query("SELECT p FROM Pedido p WHERE p.fechaCreacion BETWEEN ?1 AND ?2")
    List<Pedido> buscarPedidosEntreFechas(LocalDate desde, LocalDate hasta);

    List<Pedido> findByActivoTrue();
}

