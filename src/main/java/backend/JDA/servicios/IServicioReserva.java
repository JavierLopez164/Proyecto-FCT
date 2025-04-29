package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import backend.JDA.modelo.Reserva;

public interface IServicioReserva {
	
	public boolean insert(Reserva reserva);
	
	public boolean update(Reserva reserva);
	
	public boolean delete(String id);
	
	public List<Reserva> findAll();
	
	public Optional<Reserva> findById(String id);
	
}
