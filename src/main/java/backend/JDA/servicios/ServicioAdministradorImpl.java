package backend.JDA.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import backend.JDA.modelo.Administrador;
import backend.JDA.repositorios.AdministradorRepositorio;

@Service
public class ServicioAdministradorImpl implements IServicioAdministrador {

	@Autowired
	private AdministradorRepositorio administradorRepositorio;

	@Override
	public boolean insert(Administrador administrador) {
		boolean exito = false;

		if(!administradorRepositorio.existsById(administrador.getEmail())) {
			administrador.setToken(getJWTToken(administrador.getEmail()));
			administradorRepositorio.save(administrador);
			exito = true;
		}

		return exito;
	}

	@Override
	public boolean update(Administrador administrador) {
		boolean exito = false;

		if(!administradorRepositorio.existsById(administrador.getEmail())) {
			administradorRepositorio.save(administrador);
			exito = true;
		}

		return exito;
	}

	@Override
	public boolean delete(String email) {
		boolean exito = false;

		if(administradorRepositorio.existsById(email)) {
			administradorRepositorio.deleteById(email);
			exito = true;
		}

		return exito;
	}

	@Override
	public Optional<Administrador> findById(String email) {
		return administradorRepositorio.findById(email);
	}

	@Override
	public Administrador login(String email, String contrasenia) {
		return administradorRepositorio.findByEmailAndContrasenia(email, contrasenia);
	}

	@Override
	public String usuarioCoincidente(String email, String password) {
		return administradorRepositorio.usuarioCoincidente(email, password);
	}
}
