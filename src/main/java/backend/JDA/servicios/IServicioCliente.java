package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import backend.JDA.modelo.Cliente;

public interface IServicioCliente {

	public boolean insert(Cliente cliente);
	
	public boolean update(Cliente cliente);
	
	public boolean delete(String id);
	
	public List<Cliente> findAll();
	
	public Optional<Cliente> findById(String id);
	
}
