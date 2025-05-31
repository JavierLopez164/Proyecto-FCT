package backend.JDA.repositorios;

import backend.JDA.modelo.Comentario;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, Long> {

    @Query("SELECT c FROM Comentario c WHERE c.comida.comidaPK.nComida = ?1 AND c.comida.comidaPK.nRestaurante = ?2 ")
    List<Comentario> findByComidaId(String idComida, String restaurante);

    @Query("SELECT AVG(c.valoracion) FROM Comentario c WHERE c.comida.comidaPK.nComida = ?1 AND c.comida.comidaPK.nRestaurante = ?2")
    Double obtenerPromedioValoracionPorComida(String comida, String restaurante);

    @Transactional
    int deleteByDestacadoIsTrueAndFechaBefore(LocalDateTime fechaLimite);

    @Query("""
        SELECT COUNT(ip) > 0
        FROM Pedido p
        JOIN p.items ip
        WHERE p.cliente.email = :email
        AND ip.comida.comidaPK.nComida = :nombreComida
        AND ip.comida.comidaPK.nRestaurante = :restaurante
    """)
    boolean clientePidioComida(String email, String nombreComida, String restaurante);


}