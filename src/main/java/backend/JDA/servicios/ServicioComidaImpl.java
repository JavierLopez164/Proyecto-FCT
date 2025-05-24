package backend.JDA.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.JDA.config.DtoConverter;
import backend.JDA.modelo.Comida;
import backend.JDA.modelo.ComidaPK;
import backend.JDA.modelo.Foto;
import backend.JDA.modelo.dto.ComidaGaleriaDto;
import backend.JDA.repositorios.ComidaRepositorio;

@Service
public class ServicioComidaImpl implements IServicioComida {

	@Autowired
	private ComidaRepositorio comidaDAO;
	@Autowired 
	private DtoConverter dtoConverter;
	@Override
	public boolean insert(Comida comida) {
		// TODO Auto-generated method stub
		boolean exito = false;

		if(!comidaDAO.existsById(comida.getComidaPK())) {
			comidaDAO.save(comida);
			exito = true;
		}

		return exito;
	}

	@Override
	public boolean update(Comida comida) {
		// TODO Auto-generated method stub
		boolean exito = false;

		if(comidaDAO.existsById(comida.getComidaPK())) {
			comidaDAO.save(comida);
			exito = true;
		}

		return exito;
	}

	@Override
	public boolean delete(ComidaPK comidaPK) {
		// TODO Auto-generated method stub
		boolean exito = false;

		if(comidaDAO.existsById(comidaPK)) {
			comidaDAO.deleteById(comidaPK);
			exito = true;
		}

		return exito;
	}

	@Override
	public List<Comida> findAll() {
		// TODO Auto-generated method stub
		return  comidaDAO.findAll();
	}

	@Override
	public Optional<Comida> findById(ComidaPK comidaPK) {
		// TODO Auto-generated method stub
		return comidaDAO.findById(comidaPK);
	}

	@Override
	public boolean cambiarDescripcion(ComidaPK comidaPK, String descripcion) {
		// TODO Auto-generated method stub
		boolean exito = false;

		if (comidaDAO.existsById(comidaPK)) {
			comidaDAO.actualizaDescripcion(comidaPK, descripcion);
			exito = true;
		}

		return exito;
	}

	@Override
	public boolean cambiarPrecio(ComidaPK comidaPK, float precio) {
		// TODO Auto-generated method stub
		boolean exito = false;

		if (comidaDAO.existsById(comidaPK) && precio > 0f) {
			comidaDAO.actualizarPrecio(comidaPK, precio);
			exito = true;
		}

		return exito;
	}

	@Override
	public List<Comida> obtenerComidasDeUnRestaurante(String restaurante) {
		// TODO Auto-generated method stub
		List<Comida> listaComidas = new ArrayList<>();

		listaComidas = comidaDAO.obtenerComidasDeUnRestaurante(restaurante);

		return listaComidas;
	}

	@Override
	public List<String> obtenerTodosLosRestaurantes() {
		// TODO Auto-generated method stub
		return comidaDAO.obtenerTodosLosRestaurantes();
	}

	@Override
	public List<ComidaGaleriaDto> obtenerTodasLasComidasDeUnRestaurantes(String restaurante) {
		// TODO Auto-generated method stub
		return dtoConverter.mapAll(comidaDAO.obtenerTodasLasComidasDeUnRestaurantes(restaurante), ComidaGaleriaDto.class)  ;
	}

}