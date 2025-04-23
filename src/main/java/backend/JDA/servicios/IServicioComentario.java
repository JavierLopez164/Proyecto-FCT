package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import backend.JDA.modelo.Comentario;

public interface IServicioComentario {
	
	public boolean insert(Comentario comentario);
	
	public boolean update(Comentario comentario);
	
	public boolean delete(String id);
	
	public List<Comentario> findAll();
	
	public Optional<Comentario> findById(String id);
	
}
