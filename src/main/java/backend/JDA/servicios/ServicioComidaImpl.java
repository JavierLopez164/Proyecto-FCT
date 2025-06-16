package backend.JDA.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import backend.JDA.modelo.dto.ComidaUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.JDA.config.DtoConverter;
import backend.JDA.modelo.Comida;
import backend.JDA.modelo.ComidaPK;
import backend.JDA.modelo.Foto;
import backend.JDA.modelo.dto.ComidaGaleriaDto;
import backend.JDA.repositorios.ComidaRepositorio;
import backend.JDA.repositorios.ItemPedidoRepositorio;
import jakarta.transaction.Transactional;

@Service
public class ServicioComidaImpl implements IServicioComida {

	@Autowired
	private ComidaRepositorio comidaDAO;
	@Autowired 
	private DtoConverter dtoConverter;
	
	@Autowired
	private ItemPedidoRepositorio itemPedidoDAO;
	
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
	public Optional<Comida> update(ComidaUpdateDto comidaDto) {
		ComidaPK pk = new ComidaPK(comidaDto.getNcomida(), comidaDto.getRestaurante());
		Foto fotoExistente;

		Optional<Comida> comidaOpt = comidaDAO.findById(pk);
		if (comidaOpt.isEmpty()) return Optional.empty();

		Comida comidaExistente = comidaOpt.get();
		fotoExistente = comidaExistente.getFoto();

		 dtoConverter.mapObjetc(comidaDto, comidaExistente);
		comidaExistente.setFoto(fotoExistente);

		comidaDAO.save(comidaExistente);
		return Optional.of(comidaExistente);
	}

	@Override
	@Transactional
	public boolean delete(ComidaPK comidaPK) {
	    boolean exito = false;

	    if (comidaDAO.existsById(comidaPK)) {
	        // Buscar la Comida
	        Comida comida = comidaDAO.findById(comidaPK).orElse(null);

	        if (comida != null) {
	            // Eliminar los ItemPedido asociados
	        	itemPedidoDAO.deleteByComida(comida);

	            // Eliminar la Comida
	            comidaDAO.deleteById(comidaPK);
	            exito = true;
	        }
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
		return dtoConverter.mapAll(comidaDAO.obtenerComidasDeUnRestaurante(restaurante), ComidaGaleriaDto.class)  ;
	}

}