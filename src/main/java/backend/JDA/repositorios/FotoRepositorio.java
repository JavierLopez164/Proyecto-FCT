package backend.JDA.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Foto;

@Repository
public interface FotoRepositorio extends JpaRepository<Foto, Integer> {

}
