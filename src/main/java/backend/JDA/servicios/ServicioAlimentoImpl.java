package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.JDA.modelo.Alimento;
import backend.JDA.repositorios.AlimentoRepositorio;

@Service
public class ServicioAlimentoImpl implements IServicioAlimento {
	
	@Autowired
	private AlimentoRepositorio alimentoDAO;

	@Override
	public boolean insert(Alimento alimento) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(!alimentoDAO.existsById(alimento.getId())) {
			alimentoDAO.save(alimento);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean update(Alimento alimento) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(alimentoDAO.existsById(alimento.getId())) {
			alimentoDAO.save(alimento);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(alimentoDAO.existsById(id)) {
			alimentoDAO.deleteById(id);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public List<Alimento> findAll() {
		// TODO Auto-generated method stub
		return (List<Alimento>) alimentoDAO.findAll();
	}

	@Override
	public Optional<Alimento> findById(String id) {
		// TODO Auto-generated method stub
		return alimentoDAO.findById(id);
	}
	
}
