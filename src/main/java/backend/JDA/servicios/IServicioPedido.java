package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import backend.JDA.modelo.ComidaPK;
import backend.JDA.modelo.Pedido;

public interface IServicioPedido {

	 Optional<Pedido> crearPedidoSimple(String email, String restaurante);

	 Optional<Pedido> añadirComida(String pedidoId, ComidaPK comidaPK);

	List<Pedido> listarPedidos();
	
}
