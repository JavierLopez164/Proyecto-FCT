package backend.JDA.servicios;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import backend.JDA.controladores.AdministradorController;
import backend.JDA.modelo.Administrador;
import backend.JDA.repositorios.AdministradorRepositorio;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class ServicioAdministradorImpl implements IServicioAdministrador {

	@Autowired
	private AdministradorRepositorio administradorDao;

   
	@Override
	public boolean insert(Administrador administrador) {
		boolean exito = false;
	
		if(!administradorDao.existsById(administrador.getEmail())) {
		
			administradorDao.save(administrador);
			exito = true;
		}

		return exito;
	}

	@Override
	public boolean update(Administrador administrador) {
		boolean exito = false;

		if(!administradorDao.existsById(administrador.getEmail())) {
			administradorDao.save(administrador);
			exito = true;
		}

		return exito;
	}

	@Override
	public boolean delete(String email) {
		boolean exito = false;

		if(administradorDao.existsById(email)) {
			administradorDao.deleteById(email);
			exito = true;
		}

		return exito;
	}

	@Override
	public Optional<Administrador> findById(String email) {
		return administradorDao.findById(email);
	}


	
	

}
