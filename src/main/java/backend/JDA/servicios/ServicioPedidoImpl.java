package backend.JDA.servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import backend.JDA.config.QRUtils;
import backend.JDA.modelo.Cliente;
import backend.JDA.modelo.Comida;
import backend.JDA.modelo.ComidaPK;
import backend.JDA.repositorios.ClienteRepositorio;
import backend.JDA.repositorios.ComidaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.JDA.modelo.Pedido;
import backend.JDA.repositorios.PedidoRepositorio;

@Service
public class ServicioPedidoImpl implements IServicioPedido {

	@Autowired
	private PedidoRepositorio pedidoRepo;

	@Autowired
	private ClienteRepositorio clienteRepo;

	@Autowired
	private ComidaRepositorio comidaRepo;

	public Optional<Pedido> crearPedidoSimple(String email, String restaurante) {
		Optional<Cliente> clienteOpt = clienteRepo.findById(email);
		List<Comida> comidasRest = comidaRepo.findByComidaPKNRestaurante(restaurante);

		if (clienteOpt.isEmpty() || comidasRest.isEmpty()) return Optional.empty();

		Pedido pedido = Pedido.builder().id(UUID.randomUUID().toString()).cliente(clienteOpt.get())
				.activo(true).comidas(new ArrayList<>()).fechaCreacion(LocalDate.now()).cantidadFinal(0f).build();

		pedidoRepo.save(pedido);

		// Generamos QR hacia un esquema app://pedido/{id}
		try {
			QRUtils.generarQR("app://pedido/" + pedido.getId(), pedido.getId() + ".png");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Optional.of(pedido);
	}

	public Optional<Pedido> añadirComida(String pedidoId, ComidaPK comidaPK) {
		Optional<Pedido> pedidoOpt = pedidoRepo.findById(pedidoId);
		Optional<Comida> comidaOpt = comidaRepo.findById(comidaPK);

		if (pedidoOpt.isEmpty() || comidaOpt.isEmpty()) return Optional.empty();

		Pedido pedido = pedidoOpt.get();
		Comida comida = comidaOpt.get();

		pedido.getComidas().add(comida);
		float totalActual = pedido.getCantidadFinal();
		pedido.setCantidadFinal(totalActual + comida.getPrice());

		pedidoRepo.save(pedido);
		return Optional.of(pedido);
	}

	public List<Pedido> listarPedidos() {
		return pedidoRepo.findByActivoTrue();
	}

	public Optional<Pedido> cambiarEstadoPedido(String id, boolean nuevoEstado) {
		Optional<Pedido> pedidoOpt = pedidoRepo.findById(id);
		if (pedidoOpt.isEmpty()) return Optional.empty();

		Pedido pedido = pedidoOpt.get();
		pedido.setActivo(nuevoEstado);
		pedidoRepo.save(pedido);
		return Optional.of(pedido);
	}

	public List<Object[]> top5ComidasMasPedidas() {
		return comidaRepo.top5ComidasMasPedidas();
	}

	public List<Object[]> top5ComidasPorRestaurante(String restaurante) {
		return comidaRepo.top5ComidasPorRestaurante(restaurante);
	}

	public int pedidosUltimos7Dias() {
		LocalDate hoy = LocalDate.now();
		LocalDate hace7Dias = hoy.minusDays(7);
		return pedidoRepo.buscarPedidosEntreFechas(hace7Dias, hoy).size();
	}


}
