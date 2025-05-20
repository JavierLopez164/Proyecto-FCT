package backend.JDA.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Promocion;

@Repository
public interface PromocionRepositorio extends JpaRepository<Promocion, String> {

}
