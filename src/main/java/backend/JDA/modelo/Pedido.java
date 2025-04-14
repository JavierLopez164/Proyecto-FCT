package backend.JDA.modelo;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Pedido {
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "pk_pedido")
	private String id;
	@Column(name = "cliente")
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Cliente cliente;
	@Column(name = "comidas")
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private List<Comida> comidas;
	@Column(name = "cantidadFinal")
	private float cantidadFinal;
	
}
