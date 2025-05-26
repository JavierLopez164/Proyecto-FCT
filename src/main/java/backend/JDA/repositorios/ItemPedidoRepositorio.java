package backend.JDA.repositorios;

import backend.JDA.modelo.ItemPedido;
import backend.JDA.modelo.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepositorio extends JpaRepository<ItemPedido, Long> {
}
