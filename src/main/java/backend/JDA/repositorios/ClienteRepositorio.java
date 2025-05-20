package backend.JDA.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Cliente;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String> {


}
