package backend.JDA.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Promocion;

@Repository
public interface PromocionRepositorio extends CrudRepository<Promocion, String> {

}
