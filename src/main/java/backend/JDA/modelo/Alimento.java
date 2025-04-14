package backend.JDA.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Alimento {
	
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "pk_alimento")
	private String id;
	@Column(name = "", length = 20)
	private String nombre;
	@Column(name = "tipoAlimento")
	@Enumerated(EnumType.STRING)
	private TipoAlimento tipoAlimento;
	
}
