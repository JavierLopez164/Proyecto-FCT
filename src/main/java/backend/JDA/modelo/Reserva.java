package backend.JDA.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_cliente")
    private Cliente cliente;
	@Column(name = "fechaHora")
	private LocalDateTime fechaHora;
	@Min(1)
	@Max(5)
	@Column(name = "nPersonas")
	private int nPersonas;
	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private Estado estado;
	@Column(length = 70)
	private String restaurante;
	
}
