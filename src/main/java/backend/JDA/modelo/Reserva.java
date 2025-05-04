package backend.JDA.modelo;

import java.time.LocalDateTime;

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
public class Reserva {
	
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "pk_reserva")
	private String id;
	@Column(name = "fechaHora")
	private LocalDateTime fechaHora;
	@Column(name = "nPersonas")
	private int nPersonas;
	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private Estado estado;
	
}
