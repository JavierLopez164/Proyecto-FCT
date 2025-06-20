package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import backend.JDA.modelo.Comida;
import backend.JDA.modelo.ComidaPK;
import backend.JDA.modelo.Foto;
import backend.JDA.modelo.dto.ComidaGaleriaDto;
import backend.JDA.modelo.dto.ComidaUpdateDto;

public interface IServicioComida {

	public boolean insert(Comida comida);

	Optional<Comida> update(ComidaUpdateDto comida);

	public boolean delete(ComidaPK comidaPK);

	public List<Comida> findAll();

	public Optional<Comida> findById(ComidaPK comidaPK);

	public boolean cambiarDescripcion(ComidaPK comidaPK, String descripcion);

	public boolean cambiarPrecio(ComidaPK comidaPK, float precio);
	boolean alternarOcultar(ComidaPK pk);
	public List<String>obtenerTodosLosRestaurantes();
	public List<ComidaGaleriaDto>obtenerTodasLasComidasDeUnRestaurantes(String restaurante);
	public List<Comida> obtenerComidasDeUnRestaurante(String restaurante);

}