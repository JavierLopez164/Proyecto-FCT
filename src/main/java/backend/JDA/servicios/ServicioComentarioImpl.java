package backend.JDA.servicios;

import backend.JDA.config.DtoConverter;
import backend.JDA.modelo.*;
import backend.JDA.modelo.dto.ComentarioDTO;
import backend.JDA.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioComentarioImpl implements IServicioComentario {

	@Autowired
	private ComentarioRepositorio comentarioRepo;

	@Autowired
	private ClienteRepositorio clienteRepo;

	@Autowired
	private ComidaRepositorio comidaRepo;
	@Autowired
	private DtoConverter dtoConverter;

	@Override
	public boolean crearComentario(ComentarioDTO dto, String cliente, ComidaPK comida) {
		Optional<Cliente> clienteOpt = clienteRepo.findById(cliente);
		Optional<Comida> comidaOpt = comidaRepo.findById(comida);
		Comentario comentario;

		if (clienteOpt.isPresent() && comidaOpt.isPresent()) {
			Cliente copia = clienteOpt.get();
			System.out.println("Cliente encontrado: " + copia.getEmail() + ", rol: " + copia.getRol());

			if (copia.getRol().toString().equals("ROLE_ADMIN") || copia.getRol().toString().equals("ROLE_USER")) {
				comentario = dtoConverter.map(dto, Comentario.class);

				comentario.setCliente(copia);
				comentario.setComida(comidaOpt.get());
				comentario.setFecha(LocalDateTime.now());
				comentarioRepo.save(comentario);
				return true;
			} else {
				System.out.println("Rol no autorizado: " + copia.getRol());
			}
		} else {
			System.out.println("Cliente no encontrado con email: " + cliente);
		}

		return false;
	}


	@Override
	public boolean eliminarComentario(Long id, String emailCliente) {
		Optional<Comentario> comentarioOpt = comentarioRepo.findById(id);
		Optional<Cliente> clienteOpt = clienteRepo.findById(emailCliente);

		if (comentarioOpt.isPresent() && clienteOpt.isPresent()) {
			Cliente cliente = clienteOpt.get();
			if (cliente.getRol().toString().equals("ROLE_ADMIN")) {
				comentarioRepo.deleteById(id);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Comentario> obtenerComentariosPorComida(String comida, String restaurante) {
		return comentarioRepo.findByComidaId(comida, restaurante);
	}

	@Override
	public Optional<Comentario> findById(Long id) {
		return comentarioRepo.findById(id);
	}

	@Override
	public int obtenerPromedioValoracion(String comida, String restaurante) {
		Double promedio = comentarioRepo.obtenerPromedioValoracionPorComida(comida, restaurante);
		return promedio != null ? promedio.intValue() : 0;
	}

}
