package backend.JDA.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Alimento;

@Repository
public interface AlimentoRepositorio extends CrudRepository<Alimento, String> {

}
