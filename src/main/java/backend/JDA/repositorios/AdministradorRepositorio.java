package backend.JDA.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Administrador;

@Repository
public interface AdministradorRepositorio extends CrudRepository<Administrador, String> {

}
