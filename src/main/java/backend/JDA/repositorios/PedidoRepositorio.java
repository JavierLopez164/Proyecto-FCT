package backend.JDA.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Pedido;

@Repository
public interface PedidoRepositorio extends CrudRepository<Pedido, String> {

}
