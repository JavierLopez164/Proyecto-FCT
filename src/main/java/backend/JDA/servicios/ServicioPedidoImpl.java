package backend.JDA.servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import backend.JDA.config.DtoConverter;
import backend.JDA.config.QRUtils;
import backend.JDA.modelo.*;
import backend.JDA.modelo.dto.ItemDTO;
import backend.JDA.modelo.dto.PedidoCreadoDTO;
import backend.JDA.modelo.dto.PedidoListadoDTO;
import backend.JDA.modelo.dto.TopComidaDTO;
import backend.JDA.repositorios.ClienteRepositorio;
import backend.JDA.repositorios.ComidaRepositorio;
import backend.JDA.repositorios.ItemPedidoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.JDA.repositorios.PedidoRepositorio;

@Service
public class ServicioPedidoImpl implements IServicioPedido {

	@Autowired
	private PedidoRepositorio pedidoRepo;

	@Autowired
	private ClienteRepositorio clienteRepo;

	@Autowired
	private ComidaRepositorio comidaRepo;

	@Autowired
	private ItemPedidoRepositorio itemPedidoRepo;

	@Autowired
	private DtoConverter dtoConverter;

	public PedidoCreadoDTO crearPedidoSimple(String email, String restaurante) {
		Optional<Cliente> clienteOpt = clienteRepo.findById(email);
		List<Comida> comidasRest = comidaRepo.obtenerComidasDeUnRestaurante(restaurante);
		Optional<Pedido> pedido = Optional.empty();
		if (!clienteOpt.isEmpty() && !comidasRest.isEmpty()) {
			LocalDate hoy = LocalDate.now();
			LocalDate expiracion = hoy.plusDays(1); // fecha de expiración = mañana

			pedido = Optional.of(Pedido.builder().id(UUID.randomUUID().toString()).cliente(clienteOpt.get())
					.activo(true).items(new ArrayList<>()).fechaCreacion(hoy).fechaExpiracion(expiracion)
					.cantidadFinal(0).restaurante(restaurante).build());

			pedidoRepo.save(pedido.get());

			try {
				QRUtils.generarQR("app://pedido/" + pedido.get().getId(), pedido.get().getId() + ".png");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return dtoConverter.map(pedido, PedidoCreadoDTO.class);
	}

	public Optional<Pedido> añadirComida(String pedidoId, ComidaPK comidaPK) {
		Optional<Pedido> pedidoOpt = pedidoRepo.findById(pedidoId);
		Optional<Comida> comidaOpt = comidaRepo.findById(comidaPK);

		if (pedidoOpt.isEmpty() || comidaOpt.isEmpty())
			return Optional.empty();

		Pedido pedido = pedidoOpt.get();
		Comida comida = comidaOpt.get();

		List<ItemPedido> items = pedido.getItems();

		// Buscar si ya hay un item con esa comida
		Optional<ItemPedido> existenteOpt = items.stream().filter(i -> i.getComida().equals(comida)).findFirst();
		if (existenteOpt.isPresent()) {
			ItemPedido existente = existenteOpt.get();
			existente.setCantidad(existente.getCantidad() + 1);
			itemPedidoRepo.save(existente);
		} else {
			ItemPedido nuevoItem = ItemPedido.builder().pedido(pedido).comida(comida).cantidad(1).build();
			items.add(nuevoItem);
			itemPedidoRepo.save(nuevoItem);
		}

		// Recalcular total
		int total = items.stream().mapToInt(i -> i.getCantidad() * i.getComida().getPrice()).sum();
		pedido.setCantidadFinal(total);

		pedidoRepo.save(pedido);
		return Optional.of(pedido);
	}

	public Optional<Pedido> restarComida(String pedidoId, ComidaPK comidaPK) {
		Optional<Pedido> pedidoOpt = pedidoRepo.findById(pedidoId);
		Optional<Comida> comidaOpt = comidaRepo.findById(comidaPK);

		if (pedidoOpt.isEmpty() || comidaOpt.isEmpty())
			return Optional.empty();

		Pedido pedido = pedidoOpt.get();
		Comida comida = comidaOpt.get();

		List<ItemPedido> items = pedido.getItems();

		Optional<ItemPedido> itemOpt = items.stream().filter(i -> i.getComida().equals(comida)).findFirst();

		if (itemOpt.isPresent()) {
			ItemPedido item = itemOpt.get();
			if (item.getCantidad() > 1) {
				item.setCantidad(item.getCantidad() - 1);
				itemPedidoRepo.save(item);
			} else {
				// Si es 1, se elimina directamente
				items.remove(item);
				itemPedidoRepo.delete(item);
			}

			// Recalcular total
			int total = items.stream().mapToInt(i -> i.getCantidad() * i.getComida().getPrice()).sum();
			pedido.setCantidadFinal(total);
			pedidoRepo.save(pedido);

			return Optional.of(pedido);
		}

		return Optional.empty(); // No estaba ese item
	}

	public Optional<Pedido> eliminarComida(String pedidoId, ComidaPK comidaPK) {
		Optional<Pedido> pedidoOpt = pedidoRepo.findById(pedidoId);
		Optional<Comida> comidaOpt = comidaRepo.findById(comidaPK);

		if (pedidoOpt.isEmpty() || comidaOpt.isEmpty())
			return Optional.empty();

		Pedido pedido = pedidoOpt.get();
		Comida comida = comidaOpt.get();

		List<ItemPedido> items = pedido.getItems();

		Optional<ItemPedido> itemOpt = items.stream().filter(i -> i.getComida().equals(comida)).findFirst();

		if (itemOpt.isPresent()) {
			ItemPedido item = itemOpt.get();
			items.remove(item);
			itemPedidoRepo.delete(item);

			// Recalcular total
			int total = items.stream().mapToInt(i -> i.getCantidad() * i.getComida().getPrice()).sum();
			pedido.setCantidadFinal(total);
			pedidoRepo.save(pedido);

			return Optional.of(pedido);
		}

		return Optional.empty(); // No había nada que eliminar
	}

	public Optional<Pedido> cambiarEstadoPedido(String id, boolean nuevoEstado) {
		Optional<Pedido> pedidoOpt = pedidoRepo.findById(id);
		if (pedidoOpt.isEmpty())
			return Optional.empty();

		Pedido pedido = pedidoOpt.get();
		pedido.setActivo(nuevoEstado);
	
		pedidoRepo.save(pedido);
		return Optional.of(pedido);
	}

	public List<TopComidaDTO> top5ComidasMasPedidas() {
		return pedidoRepo.top5ComidasMasPedidas().stream().limit(5).toList();
	}

	public List<TopComidaDTO> top5ComidasPorRestaurante(String restaurante) {
		return pedidoRepo.top5ComidasPorRestaurante(restaurante).stream().limit(5).toList();
	}

	public int pedidosUltimos7Dias() {
		LocalDate hoy = LocalDate.now();
		LocalDate hace7Dias = hoy.minusDays(7);
		return pedidoRepo.buscarPedidosEntreFechas(hace7Dias, hoy).size();
	}

	public PedidoListadoDTO mapToPedidoListadoDTO(Pedido pedido) {
		PedidoListadoDTO dto = dtoConverter.map(pedido, PedidoListadoDTO.class);

		// Mapear los items manualmente si no se hace automáticamente
		List<ItemDTO> itemsDTO = pedido.getItems().stream().map(item -> {
			ItemDTO itemDTO = new ItemDTO();
			itemDTO.setNombreComida(item.getComida().getComidaPK().getNComida());
			itemDTO.setCantidad(item.getCantidad());
			itemDTO.setPrecioUnitario(item.getComida().getPrice());
			return itemDTO;
		}).collect(Collectors.toList());

		dto.setItems(itemsDTO);
		return dto;
	}

	public List<PedidoListadoDTO> listarPedidosDTO() {
		return pedidoRepo.findByActivoTrue().stream().map(this::mapToPedidoListadoDTO).collect(Collectors.toList());
	}

	
	public Optional<Pedido> aniadirComidas(String pedidoId, ComidaPK comidaPK, int cantidad,int total) {
		Optional<Pedido> pedidoOpt = pedidoRepo.findById(pedidoId);
		Optional<Comida> comidaOpt = comidaRepo.findById(comidaPK);
		
		if (pedidoOpt.isPresent() && comidaOpt.isPresent()) {
			
			pedidoOpt.get().setCantidadFinal(total);
			pedidoRepo.save(pedidoOpt.get());
			itemPedidoRepo.save(ItemPedido.builder().pedido(pedidoOpt.get()).comida(comidaOpt.get()).cantidad(cantidad).build());

		}
		return pedidoOpt;
	}
	
	
	
}
