package backend.JDA.repositorios;

import backend.JDA.modelo.ClienteAnonimo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ClienteRepositorioAnonimo extends CrudRepository<ClienteAnonimo, Long> {
    void deleteByFechaExpiracionBefore(LocalDateTime fecha);
}

