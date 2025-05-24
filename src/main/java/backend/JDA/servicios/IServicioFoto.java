package backend.JDA.servicios;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import backend.JDA.modelo.ComidaPK;
import backend.JDA.modelo.Foto;
import backend.JDA.modelo.dto.ClienteFotoDto;
import backend.JDA.modelo.dto.ComidaFotoDto;

public interface IServicioFoto {
	public Optional<ClienteFotoDto> subirImagenACloudFotoPerfil(MultipartFile imagenFichero,String email) ;
	public Optional<ComidaFotoDto> subirImagenACloudComida(MultipartFile imagenFichero,ComidaPK comidaPK,String email);
	
	public boolean update(Foto foto);
	
	public boolean delete(int id);

	public List<Foto> findAll();
	
	public Optional<Foto> findById(int id);
	
}
