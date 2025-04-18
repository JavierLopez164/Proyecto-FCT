package backend.JDA.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Reserva;

@Repository
public interface ReservaRepositorio extends CrudRepository<Reserva, String> {

}
