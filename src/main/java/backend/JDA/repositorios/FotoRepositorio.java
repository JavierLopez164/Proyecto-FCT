package backend.JDA.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Foto;

@Repository
public interface FotoRepositorio extends JpaRepository<Foto, Integer> {

}
