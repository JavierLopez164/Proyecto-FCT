package backend.JDA.servicios;

import backend.JDA.modelo.Comentario;

import java.util.List;
import java.util.Optional;

public interface IServicioComentario {
	boolean crearComentario(Comentario comentario, String cliente, String comida);
	boolean eliminarComentario(Long id, String emailCliente);
	List<Comentario> obtenerComentariosPorComida(String idComida);
	Optional<Comentario> findById(Long id);
}
