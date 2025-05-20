package backend.JDA.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Reserva;

@Repository
public interface ReservaRepositorio extends JpaRepository<Reserva, String> {

}
