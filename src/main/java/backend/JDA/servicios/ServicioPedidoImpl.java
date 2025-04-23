package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.JDA.modelo.Pedido;
import backend.JDA.repositorios.PedidoRepositorio;

@Service
public class ServicioPedidoImpl implements IServicioPedido {

	@Autowired
	private PedidoRepositorio pedidoDAO;

	@Override
	public boolean insert(Pedido pedido) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(!pedidoDAO.existsById(pedido.getId())) {
			pedidoDAO.save(pedido);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean update(Pedido pedido) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(pedidoDAO.existsById(pedido.getId())) {
			pedidoDAO.save(pedido);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(pedidoDAO.existsById(id)) {
			pedidoDAO.deleteById(id);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public List<Pedido> findAll() {
		// TODO Auto-generated method stub
		return (List<Pedido>) pedidoDAO.findAll();
	}

	@Override
	public Optional<Pedido> findById(String id) {
		// TODO Auto-generated method stub
		return pedidoDAO.findById(id);
	}
	
}
