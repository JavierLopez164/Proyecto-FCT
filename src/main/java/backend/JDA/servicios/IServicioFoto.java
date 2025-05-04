package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import backend.JDA.modelo.Foto;

public interface IServicioFoto {
	
	public boolean insert(Foto foto);
	
	public boolean update(Foto foto);
	
	public boolean delete(Long id);
	
	public List<Foto> findAll();
	
	public Optional<Foto> findById(Long id);
	
}
