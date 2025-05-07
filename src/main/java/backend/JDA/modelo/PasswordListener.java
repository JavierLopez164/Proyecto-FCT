package backend.JDA.modelo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class PasswordListener {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@PrePersist
	@PreUpdate
	
	public void traspasarPassword(Cliente cliente) {
		
		if(cliente!=null)
			cliente.setContrasenia(passwordEncoder.encode(cliente.getContrasenia()));
	}
}
