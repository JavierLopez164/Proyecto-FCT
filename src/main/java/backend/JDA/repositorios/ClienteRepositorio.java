package backend.JDA.repositorios;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Cliente;

import java.time.LocalDateTime;

@Repository
public interface ClienteRepositorio extends CrudRepository<Cliente, String> {
    //void deleteByFechaExpiracionBefore(LocalDateTime fecha);

}
