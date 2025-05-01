package backend.JDA.servicios;

import backend.JDA.modelo.Cliente;
import backend.JDA.modelo.ClienteAnonimo;
import backend.JDA.modelo.ClienteRegistrado;

import java.time.LocalDateTime;
import java.util.Optional;


public interface IServicioClienteAnonimo {

    boolean insert(ClienteAnonimo cliente);
    boolean delete(Long id);
    Optional<ClienteAnonimo> findById(Long id);
}
