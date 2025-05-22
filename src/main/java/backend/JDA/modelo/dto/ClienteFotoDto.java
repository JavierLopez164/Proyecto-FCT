package backend.JDA.modelo.dto;

import java.time.LocalDate;

import backend.JDA.modelo.Foto;
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
public class ClienteFotoDto {
	@EqualsAndHashCode.Include
	private String email;
	
	private Foto foto;
}
