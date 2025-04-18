package backend.JDA.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Comida;

@Repository
public interface ComidaRepositorio extends CrudRepository<Comida, String> {

}
