package backend.JDA.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Cliente;
import backend.JDA.modelo.ClienteRegistrado;

@Repository
public interface ClienteRepositorio extends CrudRepository<ClienteRegistrado, Long> {

    boolean existsByEmail(String email);

    @Query("SELECT u.token FROM ClienteRegistrado u WHERE u.email=?1 and u.contrasenia=?2")
    String usuarioCoincidente(String email,String password);
}
