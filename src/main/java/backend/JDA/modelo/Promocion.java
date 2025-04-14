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
public class Promocion {
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "pk_promocion")
	private String id;
	@Column(name = "descuento")
	private float descuento;
	@Column(name = "fechaInicio")
	private LocalDateTime fechaInicio;
	@Column(name = "fechaFin")
	private LocalDateTime fechaFin;
	
}
