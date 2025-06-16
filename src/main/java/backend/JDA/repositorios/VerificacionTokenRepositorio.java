package backend.JDA.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.JDA.modelo.Cliente;
import backend.JDA.modelo.VerificacionToken;

public interface VerificacionTokenRepositorio extends JpaRepository<VerificacionToken, Long> {
	 Optional<VerificacionToken> findByToken(String token);
	 
	 int deleteByCliente(Cliente cliente);
	 
}
