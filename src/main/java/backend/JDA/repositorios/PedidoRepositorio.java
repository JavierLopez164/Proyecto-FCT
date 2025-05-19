package backend.JDA.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import backend.JDA.modelo.Pedido;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PedidoRepositorio extends CrudRepository<Pedido, String> {

    @Query("SELECT p FROM Pedido p WHERE p.fechaCreacion BETWEEN ?1 AND ?2")
    List<Pedido> buscarPedidosEntreFechas(LocalDate desde, LocalDate hasta);

    List<Pedido> findByActivoTrue();
}

