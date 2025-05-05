package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.JDA.modelo.Cliente;
import backend.JDA.modelo.ClienteRegistrado;
import backend.JDA.repositorios.ClienteRegistradoRepositorio;
import backend.JDA.repositorios.ClienteRepositorio;

@Service
public class ServicioClienteImpl implements IServicioClienteRegistrado {
	
	@Autowired
	private ClienteRegistradoRepositorio clienteDAO;

	

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
	public boolean insert(ClienteRegistrado cliente) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(ClienteRegistrado cliente) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
