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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import backend.JDA.config.DtoConverter;
import backend.JDA.config.JwtConstant;
import backend.JDA.modelo.Cliente;
import backend.JDA.modelo.Rol;

import backend.JDA.repositorios.ClienteRepositorio;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Service
public class ServicioCliente implements IServicioCliente {
	@Autowired private ClienteRepositorio clienteDao;
	@Autowired private DtoConverter dtoConverter;


	@Override
	public boolean registrarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		boolean exito = false;
		if(!clienteDao.existsById((cliente.getEmail()))){
			
			clienteDao.save(cliente);
			exito = true;
		}

		return exito;
	}


	@Override
	public Optional<Cliente> findById(String email) {
		// TODO Auto-generated method stub
		return clienteDao.findById(email);
	}


	@Override
	public boolean actualizarCliente(Cliente clm) {
		// TODO Auto-generated method stub
		boolean encontrado=false;
		if(clienteDao.existsById(clm.getEmail())) {
			clienteDao.save(clm);
			encontrado=true;
		}
		return encontrado;
	}


}
