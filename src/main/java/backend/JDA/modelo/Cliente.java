package backend.JDA.modelo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Cliente {

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@EqualsAndHashCode.Include
	@Id
	@Email(message = "El correo debe ser válido")
	@NotBlank(message = "El correo no puede estar vacío")
	@Column(name = "pk_email", length = 40, nullable = false)
	private String email;

	@Column(name = "nombre", length = 20, nullable = false)
	private String nombre;

	@Column(name = "contrasenia", length = 40, nullable = false)
	private String contrasenia;
	
	 @Enumerated(EnumType.STRING)
	 private Rol rol;
	 
	 private String token;
	 private boolean expirado;
	 
	
	
}
