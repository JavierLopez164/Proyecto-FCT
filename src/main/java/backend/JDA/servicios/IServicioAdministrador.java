package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import backend.JDA.modelo.Administrador;

public interface IServicioAdministrador {

	public boolean insert(Administrador administrador);
	
	public boolean update(Administrador administrador);
	
	public boolean delete(String id);
	
	public List<Administrador> findAll();
	
	public Optional<Administrador> findById(String id);
	
}
