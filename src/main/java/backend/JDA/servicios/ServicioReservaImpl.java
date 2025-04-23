package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.JDA.modelo.Reserva;
import backend.JDA.repositorios.ReservaRepositorio;

@Service
public class ServicioReservaImpl implements IServicioReserva {

	@Autowired
	private ReservaRepositorio reservaDAO;

	@Override
	public boolean insert(Reserva reserva) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(!reservaDAO.existsById(reserva.getId())) {
			reservaDAO.save(reserva);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean update(Reserva reserva) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(reservaDAO.existsById(reserva.getId())) {
			reservaDAO.save(reserva);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(reservaDAO.existsById(id)) {
			reservaDAO.deleteById(id);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public List<Reserva> findAll() {
		// TODO Auto-generated method stub
		return (List<Reserva>) reservaDAO.findAll();
	}

	@Override
	public Optional<Reserva> findById(String id) {
		// TODO Auto-generated method stub
		return reservaDAO.findById(id);
	}
	
}
