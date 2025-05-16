package backend.JDA.servicios;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import backend.JDA.modelo.Foto;

public interface IServicioFoto {
	public boolean subirImagenACloud(MultipartFile imagenFichero,String email) throws IOException;
	
	public boolean update(Foto foto);
	
	public boolean delete(Long id);
	
	public List<Foto> findAll();
	
	public Optional<Foto> findById(Long id);
	
}
