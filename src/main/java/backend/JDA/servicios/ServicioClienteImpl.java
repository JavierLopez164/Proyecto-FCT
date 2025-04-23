package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.JDA.modelo.Cliente;
import backend.JDA.repositorios.ClienteRepositorio;

@Service
public class ServicioClienteImpl implements IServicioCliente {
	
	@Autowired
	private ClienteRepositorio clienteDAO;

	@Override
	public boolean insert(Cliente cliente) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(!clienteDAO.existsById(cliente.getId())) {
			clienteDAO.save(cliente);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean update(Cliente cliente) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(clienteDAO.existsById(cliente.getId())) {
			clienteDAO.save(cliente);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(clienteDAO.existsById(id)) {
			clienteDAO.deleteById(id);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return (List<Cliente>) clienteDAO.findAll();
	}

	@Override
	public Optional<Cliente> findById(String id) {
		// TODO Auto-generated method stub
		return clienteDAO.findById(id);
	}
	
}
