package backend.JDA.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Comentario;

@Repository
public interface ComentarioRepositorio extends CrudRepository<Comentario, String> {

}
