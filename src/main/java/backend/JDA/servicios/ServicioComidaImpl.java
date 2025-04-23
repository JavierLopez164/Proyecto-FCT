package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.JDA.modelo.Comida;
import backend.JDA.repositorios.ComidaRepositorio;

@Service
public class ServicioComidaImpl implements IServicioComida {

	@Autowired
	private ComidaRepositorio comidaDAO;

	@Override
	public boolean insert(Comida comida) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(!comidaDAO.existsById(comida.getId())) {
			comidaDAO.save(comida);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean update(Comida comida) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(comidaDAO.existsById(comida.getId())) {
			comidaDAO.save(comida);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(comidaDAO.existsById(id)) {
			comidaDAO.deleteById(id);
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
	public Optional<Comida> findById(String id) {
		// TODO Auto-generated method stub
		return comidaDAO.findById(id);
	}
	
}
