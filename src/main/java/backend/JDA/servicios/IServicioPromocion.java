package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import backend.JDA.modelo.Promocion;

public interface IServicioPromocion {
	
	public boolean insert(Promocion promocion);
	
	public boolean update(Promocion promocion);
	
	public boolean delete(String id);
	
	public List<Promocion> findAll();
	
	public Optional<Promocion> findById(String id);
	
}
