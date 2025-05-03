package backend.JDA.servicios;

import java.util.Optional;

import backend.JDA.modelo.Cliente;


public interface IServicioCliente {
	boolean registrarCliente(Cliente c);

	public Optional<Cliente> findById(String email);
}
