package backend.JDA.repositorios;

import backend.JDA.modelo.Comentario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepositorio extends CrudRepository<Comentario, Long> {

    @Query("SELECT c FROM Comentario c WHERE c.comida = :idComida") //Cambiar por c.comida.id
    List<Comentario> findByComidaId(String idComida);

    @Query("SELECT AVG(c.valoracion) FROM Comentario c WHERE c.comida = :comida")
    Double obtenerPromedioValoracionPorComida(String comida);

}
