package backend.JDA.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Comida implements Serializable {

	@EqualsAndHashCode.Include
	@EmbeddedId
	private ComidaPK comidaPK;

	@Column(length = 200)
	private String description;

	@Column
	private float price;

	@Column
	private String category;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "comida_attributes", joinColumns = {
			@JoinColumn(name = "nombre", referencedColumnName = "nComida"),
			@JoinColumn(name = "restaurante", referencedColumnName = "nRestaurante")
	})
	@Enumerated(EnumType.STRING)
	@Column(name = "attribute")
	private List<Sabor> attributes;


	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "comida_features", joinColumns = {
			@JoinColumn(name = "nombre", referencedColumnName = "nComida"),
			@JoinColumn(name = "restaurante", referencedColumnName = "nRestaurante")
	})
	@Column(name = "feature")
	private List<String> features;

	@Column
	private int preparationTime;

	@Column
	private String imageUrl;
}