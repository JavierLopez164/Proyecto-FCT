package backend.JDA.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Foto;

@Repository
public interface FotoRepositorio extends CrudRepository<Foto, String> {

}
