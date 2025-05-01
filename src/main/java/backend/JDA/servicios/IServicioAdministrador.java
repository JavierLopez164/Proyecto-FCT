package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import backend.JDA.modelo.Administrador;

public interface IServicioAdministrador {
	boolean insert(Administrador administrador);
	boolean update(Administrador administrador);
	boolean delete(String id);
	Optional<Administrador> findById(String id);
	String administradorCoincidente(String email,String password);
}
