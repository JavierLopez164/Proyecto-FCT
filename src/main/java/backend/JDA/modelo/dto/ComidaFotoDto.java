package backend.JDA.modelo.dto;

import backend.JDA.modelo.ComidaPK;
import backend.JDA.modelo.Foto;
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
public class ComidaFotoDto {
	@EqualsAndHashCode.Include
	private ComidaPK comidaPK;
	
	private String imagenUrl;
}
