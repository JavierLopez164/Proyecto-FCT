package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import backend.JDA.modelo.Alimento;

public interface IServicioAlimento {

	public boolean insert(Alimento alimento);
	
	public boolean update(Alimento alimento);
	
	public boolean delete(String id);
	
	public List<Alimento> findAll();
	
	public Optional<Alimento> findById(String id);
	
}
