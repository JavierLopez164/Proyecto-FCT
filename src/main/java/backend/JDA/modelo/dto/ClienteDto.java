package backend.JDA.modelo.dto;

import java.time.LocalDate;

import backend.JDA.modelo.Rol;
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
public class ClienteDto {
	@EqualsAndHashCode.Include
	private String email;
	private String nombre;
	private String contrasenia;
	private LocalDate fechaCreacion;
	private Rol rol;
	private String imagenUrl;
}
