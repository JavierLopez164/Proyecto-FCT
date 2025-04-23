package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.JDA.modelo.Promocion;
import backend.JDA.repositorios.PromocionRepositorio;

@Service
public class ServicioPromocionImpl implements IServicioPromocion {

	@Autowired
	private PromocionRepositorio promocionDAO;

	@Override
	public boolean insert(Promocion promocion) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(!promocionDAO.existsById(promocion.getId())) {
			promocionDAO.save(promocion);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean update(Promocion promocion) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(promocionDAO.existsById(promocion.getId())) {
			promocionDAO.save(promocion);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(promocionDAO.existsById(id)) {
			promocionDAO.deleteById(id);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public List<Promocion> findAll() {
		// TODO Auto-generated method stub
		return (List<Promocion>) promocionDAO.findAll();
	}

	@Override
	public Optional<Promocion> findById(String id) {
		// TODO Auto-generated method stub
		return promocionDAO.findById(id);
	}
	
}
