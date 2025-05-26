package backend.JDA.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "cliente", nullable = false)
	private Cliente cliente;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<ItemPedido> items;

	@Column(nullable = false)
	private int cantidadFinal;

	private boolean activo;
	private LocalDate fechaCreacion;

	private LocalDate fechaExpiracion;

	private String restaurante;

	
}
