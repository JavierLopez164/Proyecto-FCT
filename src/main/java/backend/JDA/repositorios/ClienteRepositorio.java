package backend.JDA.repositorios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.JDA.modelo.Cliente;
import jakarta.transaction.Transactional;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String> {

	@Transactional
	int deleteByActivoIsFalseAndFechaBefore(LocalDateTime fechaLimite);
	
	List<Cliente> findByActivoIsFalse();

}
