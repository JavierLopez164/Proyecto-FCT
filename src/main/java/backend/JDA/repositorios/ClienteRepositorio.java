package backend.JDA.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Cliente;

@Repository
public interface ClienteRepositorio extends CrudRepository<Cliente, String>{

}
