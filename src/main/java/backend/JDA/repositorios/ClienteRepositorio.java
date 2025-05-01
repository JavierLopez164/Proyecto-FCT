package backend.JDA.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Cliente;
import backend.JDA.modelo.ClienteRegistrado;

@Repository
public interface ClienteRepositorio extends CrudRepository<Cliente, String> {

	  @Query("SELECT c.token FROM Cliente c WHERE c.email=?1 and c.contrasenia=?2")
	    String usuarioCoincidente(String email,String password);
}
