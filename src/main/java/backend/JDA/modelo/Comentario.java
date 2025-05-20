package backend.JDA.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comentario {
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 255)
	private String contenido;

	@Column(nullable = false)
	private LocalDateTime fecha;

	@Column(nullable = false)
	@Min(value = 1, message = "La valoración mínima es 1")
	@Max(value = 5, message = "La valoración máxima es 5")
	private int valoracion;

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@ManyToOne(optional = false)
	@JoinColumn(name = "cliente_email")
	private Cliente cliente;

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@ManyToOne(optional = false)
	private Comida comida;
}
