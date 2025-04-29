package backend.JDA.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.JDA.modelo.Cliente;
import backend.JDA.modelo.ClienteRegistrado;
import backend.JDA.repositorios.ClienteRepositorio;

@Service
public class ServicioClienteRegistradoImpl implements IServicioClienteRegistrado {
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;

	@Override
	public boolean insert(ClienteRegistrado cliente) {
		boolean exito = false;
		
		if(!clienteRepositorio.existsByEmail((cliente.getEmail()))){
			clienteRepositorio.save(cliente);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean update(ClienteRegistrado cliente) {
		boolean exito = false;
		
		if(!clienteRepositorio.existsByEmail(cliente.getEmail())) {
			clienteRepositorio.save(cliente);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean delete(String email, String password) {
		boolean exito = false;
		Optional<ClienteRegistrado> cliente = login(email, password);
		
		if(cliente.isPresent()) {
			clienteRepositorio.delete(cliente.get());
			exito = true;
		}
		
		return exito;
	}

	@Override
	public Optional<ClienteRegistrado> login(String email, String contrasenia) {
		return Optional.ofNullable(clienteRepositorio.findByEmailAndContrasenia(email, contrasenia));
	}
}
