package backend.JDA.repositorios;

import backend.JDA.modelo.Comentario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, Long> {

    @Query("SELECT c FROM Comentario c WHERE c.comida.comidaPK.nComida = ?1 AND c.comida.comidaPK.nRestaurante = ?2 ")
    List<Comentario> findByComidaId(String idComida, String restaurante);

    @Query("SELECT AVG(c.valoracion) FROM Comentario c WHERE c.comida.comidaPK.nComida = ?1 AND c.comida.comidaPK.nRestaurante = ?2")
    Double obtenerPromedioValoracionPorComida(String comida, String restaurante);


}