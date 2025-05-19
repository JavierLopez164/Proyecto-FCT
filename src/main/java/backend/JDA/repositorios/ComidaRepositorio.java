package backend.JDA.repositorios;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Comida;
import backend.JDA.modelo.ComidaPK;

@Repository
public interface ComidaRepositorio extends MongoRepository<Comida, ComidaPK> {

    // Buscar todas las comidas de un restaurante (por campo dentro del ID)
	List<Comida> findByComidaPKNRestaurante(String nRestaurante);

    // Las siguientes operaciones de actualización deben implementarse en el servicio:
    // - actualizarDescripción
    // - actualizarPrecio
    // - actualizarValoración

}