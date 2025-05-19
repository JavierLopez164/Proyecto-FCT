package backend.JDA.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.JDA.modelo.Comida;
import backend.JDA.modelo.ComidaPK;
import backend.JDA.repositorios.ComidaRepositorio;

@Service
public class ServicioComidaImpl implements IServicioComida {

	@Autowired
	private ComidaRepositorio comidaDAO;

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
		return (List<Comida>) comidaDAO.findAll();
	}

	@Override
	public Optional<Comida> findById(ComidaPK comidaPK) {
		// TODO Auto-generated method stub
		return comidaDAO.findById(comidaPK);
	}

	@Override
	public boolean cambiarDescripcion(ComidaPK comidaPK, String descripcion) {
		// TODO Auto-generated method stub
        Optional<Comida> optComida = comidaDAO.findById(comidaPK);
        boolean exito = false;
        
        if (optComida.isPresent()) {
            Comida comida = optComida.get();
            comida.setDescripcion(descripcion);
            comidaDAO.save(comida);
            exito = true;
        }
        
        return exito;
	}

	@Override
	public boolean cambiarPrecio(ComidaPK comidaPK, float precio) {
		// TODO Auto-generated method stub
		boolean exito = false;

        Optional<Comida> optComida = comidaDAO.findById(comidaPK);
        if (optComida.isPresent() && precio <= 0f) {
            Comida comida = optComida.get();
            comida.setPrecio(precio);
            comidaDAO.save(comida);
            exito = true;
        }
        return exito;
	}

	@Override
	public boolean cambiarValoracion(ComidaPK comidaPK, int valoracion) {
		// TODO Auto-generated method stub
		boolean exito = false;

        Optional<Comida> optComida = comidaDAO.findById(comidaPK);
        if (optComida.isPresent() && (valoracion > 0 || valoracion <= 5)) {
            Comida comida = optComida.get();
            comida.setValoracion(valoracion);
            comidaDAO.save(comida);
            exito = true;
        }
        return exito;
    }
	

	@Override
	public List<Comida> obtenerComidasDeUnRestaurante(String restaurante) {
		// TODO Auto-generated method stub
		return comidaDAO.findByComidaPKNRestaurante(restaurante);
	}
	
}