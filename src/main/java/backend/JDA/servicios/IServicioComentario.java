package backend.JDA.servicios;

import backend.JDA.modelo.Comentario;
import backend.JDA.modelo.ComidaPK;
import backend.JDA.modelo.dto.ComentarioDTO;

import java.util.List;
import java.util.Optional;

public interface IServicioComentario {
	boolean crearComentario(ComentarioDTO comentario, String cliente, ComidaPK comida);
	boolean eliminarComentario(Long id, String emailCliente);
	List<Comentario> obtenerComentariosPorComida(String idComida, String restaurante);
	Optional<Comentario> findById(Long id);
	int obtenerPromedioValoracion(String comida, String restaurante);

}
