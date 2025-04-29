package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import backend.JDA.modelo.Pedido;

public interface IServicioPedido {
	
	public boolean insert(Pedido pedido);
	
	public boolean update(Pedido pedido);
	
	public boolean delete(String id);
	
	public List<Pedido> findAll();
	
	public Optional<Pedido> findById(String id);
	
}
