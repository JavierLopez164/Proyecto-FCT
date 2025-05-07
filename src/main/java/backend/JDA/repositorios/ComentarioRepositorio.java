package backend.JDA.repositorios;

import backend.JDA.modelo.Comentario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ComentarioRepositorio extends CrudRepository<Comentario, Long> {

    @Query("SELECT c FROM Comentario c WHERE c.comida = :idComida") //Cambiar por c.comida.id
    List<Comentario> findByComidaId(String idComida);
}
