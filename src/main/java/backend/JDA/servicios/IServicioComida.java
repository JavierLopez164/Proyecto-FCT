package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import backend.JDA.modelo.Comida;

public interface IServicioComida {
	
	public boolean insert(Comida comida);
	
	public boolean update(Comida comida);
	
	public boolean delete(String id);
	
	public List<Comida> findAll();
	
	public Optional<Comida> findById(String id);
	
}
