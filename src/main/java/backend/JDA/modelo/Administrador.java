package backend.JDA.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
public class Administrador {
	
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "pk_administrador")
	private String id;
	@Column(name = "nombre", length = 20)
	private String nombre;
	@Column(name = "email", length = 40)
	private String email;
	@Column(name = "contrasenia", length = 40)
	private String contrasenia;
	
}
