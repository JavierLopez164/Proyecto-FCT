package backend.JDA.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@DiscriminatorValue("CR")
public class ClienteRegistrado extends Cliente{
	
	@Column(name = "nombre", length = 20)
	private String nombre;
	@Column(name = "email", length = 40)
	private String email;
	@Column(name = "contrasenia", length = 40)
	private String contrasenia;
	/*@Column(name = "foto")
	private Foto foto;*/
	
}
