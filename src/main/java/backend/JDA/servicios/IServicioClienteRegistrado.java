package backend.JDA.servicios;

import java.util.Optional;

import backend.JDA.modelo.Cliente;
import backend.JDA.modelo.ClienteRegistrado;

public interface IServicioClienteRegistrado {

    boolean insert(ClienteRegistrado cliente);

	boolean update(ClienteRegistrado cliente);

	boolean delete(String email, String contrasena);
	Optional<ClienteRegistrado> login(String email, String contrasenia);
	String usuarioCoincidente(String email,String password);
}
