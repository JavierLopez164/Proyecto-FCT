package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import backend.JDA.modelo.Comida;
import backend.JDA.modelo.ComidaPK;

public interface IServicioComida {

	public boolean insert(Comida comida);

	public boolean update(Comida comida);

	public boolean delete(ComidaPK comidaPK);

	public List<Comida> findAll();

	public Optional<Comida> findById(ComidaPK comidaPK);

	public boolean cambiarDescripcion(ComidaPK comidaPK, String descripcion);

	public boolean cambiarPrecio(ComidaPK comidaPK, float precio);


	public List<Comida> obtenerComidasDeUnRestaurante(String restaurante);

}