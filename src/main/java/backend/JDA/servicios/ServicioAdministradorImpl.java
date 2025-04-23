package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.JDA.modelo.Administrador;
import backend.JDA.repositorios.AdministradorRepositorio;

@Service
public class ServicioAdministradorImpl implements IServicioAdministrador {
	
	@Autowired
	private AdministradorRepositorio administradorDAO;

	@Override
	public boolean insert(Administrador administrador) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(!administradorDAO.existsById(administrador.getId())) {
			administradorDAO.save(administrador);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean update(Administrador administrador) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(administradorDAO.existsById(administrador.getId())) {
			administradorDAO.save(administrador);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(administradorDAO.existsById(id)) {
			administradorDAO.deleteById(id);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public List<Administrador> findAll() {
		// TODO Auto-generated method stub
		return (List<Administrador>) administradorDAO.findAll();
	}

	@Override
	public Optional<Administrador> findById(String id) {
		// TODO Auto-generated method stub
		return administradorDAO.findById(id);
	}

}
