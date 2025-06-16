package backend.JDA.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import backend.JDA.config.DtoConverter;
import backend.JDA.config.JwtConstant;
import backend.JDA.modelo.Cliente;
import backend.JDA.modelo.Rol;

import backend.JDA.repositorios.ClienteRepositorio;
import backend.JDA.repositorios.VerificacionTokenRepositorio;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
@Service
public class ServicioCliente implements IServicioCliente {
	@Autowired private ClienteRepositorio clienteDao;
	@Autowired private DtoConverter dtoConverter;
	@Autowired
	private VerificacionService verificacionService;
	@Autowired
	private VerificacionTokenRepositorio verificacionDAO;


	@Override
	public boolean registrarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		boolean exito = false;
		if(!clienteDao.existsById((cliente.getEmail()))){
			
			cliente.setActivo(false);
			clienteDao.save(cliente);
			verificacionService.crearYEnviarToken(cliente);
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
	
	// Ejecutar cada 12 horas
	@Scheduled(fixedRate = 1000 * 60 * 60) // cada 10 segundos para pruebas
	@Transactional
	public void eliminarClientesSinRegistrar() {
		LocalDateTime fechaLimite = LocalDateTime.now().minusMinutes(1); // o .minusSeconds(10) para pruebas
		List<Cliente> clientesInactivos = clienteDao.findByActivoIsFalse();
		clientesInactivos.forEach(verificacionDAO::deleteByCliente);
		int eliminados = clienteDao.deleteByActivoIsFalseAndFechaBefore(fechaLimite);
		System.out.println("Clientes eliminados automáticamente: " + eliminados);
	}


}
