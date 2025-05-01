package backend.JDA.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
public class Pedido {
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "pk_pedido")
	private String id;
	@JoinColumn(name = "cliente")
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Cliente cliente;
	@Column(name = "comidas")
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private List<Comida> comidas;
	@Column(name = "cantidadFinal")
	private float cantidadFinal;
	
}
