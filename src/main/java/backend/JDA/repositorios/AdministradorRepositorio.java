package backend.JDA.repositorios;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Administrador;
import backend.JDA.modelo.Cliente;
@Repository

public interface AdministradorRepositorio extends CrudRepository<Administrador, String> {

  
}
