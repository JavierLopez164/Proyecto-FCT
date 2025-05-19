package backend.JDA.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

//@Entity
@Document(collection = "pedido")
public class Pedido {
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "pk_pedido")
	private String id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "cliente", nullable = false)
	private Cliente cliente;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "pedido_id") // o utilizar tabla intermedia si es necesario
	private List<Comida> comidas;

	private float cantidadFinal;

	
}
