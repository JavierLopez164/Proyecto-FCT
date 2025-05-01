package backend.JDA.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
	@Email(message = "El correo debe ser válido")
	@NotBlank(message = "El correo no puede estar vacío")
	@Column(name = "email", length = 40, nullable = false)
	private String email;

	@Column(name = "nombre", length = 20, nullable = false)
	private String nombre;

	@Column(name = "contrasenia", length = 40, nullable = false)
	private String contrasenia;

	private String token;
}
