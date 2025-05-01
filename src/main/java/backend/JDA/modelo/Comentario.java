package backend.JDA.modelo;

import java.time.LocalDateTime;

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
public class Comentario {
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "pk_")
	private String id;
	@Column(name = "", length = 255)
	private String contenido;
	@Column(name = "fecha")
	private LocalDateTime fecha;
	@Column(name = "valoracion")
	private int valoracion;
	
}
