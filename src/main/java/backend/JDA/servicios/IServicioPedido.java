package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import backend.JDA.modelo.ComidaPK;
import backend.JDA.modelo.Pedido;
import backend.JDA.modelo.dto.PedidoCreadoDTO;
import backend.JDA.modelo.dto.PedidoListadoDTO;
import backend.JDA.modelo.dto.TopComidaDTO;

public interface IServicioPedido {

	PedidoCreadoDTO crearPedidoSimple(String email, String restaurante);
	Optional<Pedido> añadirComida(String pedidoId, ComidaPK comidaPK);
	Optional<Pedido> restarComida(String pedidoId, ComidaPK comidaPK);
	Optional<Pedido> eliminarComida(String pedidoId, ComidaPK comidaPK);
	List<TopComidaDTO> top5ComidasMasPedidas();
	List<TopComidaDTO> top5ComidasPorRestaurante(String restaurante);
	Optional<Pedido> cambiarEstadoPedido(String id, boolean nuevoEstado);
	int pedidosUltimos7Dias();
	List<PedidoListadoDTO> listarPedidosDTO();
	Optional<Pedido> aniadirComidas(String pedidoId, ComidaPK comidaPK, int cantidad,int total);
	
}
