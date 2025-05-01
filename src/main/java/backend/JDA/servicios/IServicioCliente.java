package backend.JDA.servicios;

import backend.JDA.modelo.Cliente;

public interface IServicioCliente {
	boolean registrarCliente(Cliente c);
	String login(String email,String password);
}
