package backend.JDA.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Administrador;

@Repository
public interface AdministradorRepositorio extends CrudRepository<Administrador, String> {
    
    @Query("SELECT a FROM Administrador a WHERE a.email = ?1 AND a.contrasenia = ?2")
    Administrador findByEmailAndContrasenia(String email, String contrasenia);

}
