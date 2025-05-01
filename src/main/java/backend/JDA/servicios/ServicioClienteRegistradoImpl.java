package backend.JDA.servicios;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;



import backend.JDA.modelo.Cliente;
import backend.JDA.modelo.ClienteRegistrado;
import backend.JDA.repositorios.ClienteRegistradoRepositorio;
import backend.JDA.repositorios.ClienteRepositorio;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class ServicioClienteRegistradoImpl implements IServicioClienteRegistrado {
	
	@Autowired
	private ClienteRegistradoRepositorio clienteDao;

	

	@Override
	public boolean insert(ClienteRegistrado cliente) {
		boolean exito = false;
		if(!clienteDao.existsById((cliente.getEmail()))){
		
			clienteDao.save(cliente);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean update(ClienteRegistrado cliente) {
		boolean exito = false;
		
		if(!clienteDao.existsById(cliente.getEmail())) {
			clienteDao.save(cliente);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean delete(String email) {
		boolean exito = false;

		if(clienteDao.existsById(email)) {
			clienteDao.deleteById(email);
			exito = true;
		}

		return exito;
	}

}
