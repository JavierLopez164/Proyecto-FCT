package backend.JDA.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Cliente;
import backend.JDA.modelo.ClienteRegistrado;

@Repository
public interface ClienteRepositorio extends CrudRepository<ClienteRegistrado, Long> {
    
    @Query("SELECT c FROM ClienteRegistrado c WHERE c.email = ?1 AND c.contrasenia = ?2")
    ClienteRegistrado findByEmailAndContrasenia(String email, String contrasenia);

    boolean existsByEmail(String email);
}
