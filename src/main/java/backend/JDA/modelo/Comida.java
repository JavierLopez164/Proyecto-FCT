package backend.JDA.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
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

	private int price;

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
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "fotoId")
	private Foto foto;

	@Column(nullable = false)
	private boolean ocultar;


}

