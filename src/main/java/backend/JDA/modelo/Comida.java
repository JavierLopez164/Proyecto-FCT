package backend.JDA.modelo;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Comida {
	
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "pk_comida")
	private String id;
	@Column(name = "nombre", length = 20)
	private String nombre;
	@Column(name = "descripcion", length = 40)
	private String descripcion;
	@Column(name = "precio")
	private float precio;
	@Column(name = "sabor")
	@Enumerated(EnumType.STRING)
	private Sabor sabor;
	@Column(name = "valoracion")
	private int valoracion;
	@Column(name = "alimentos")
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private List<Alimento> alimentos;
	@Column(name = "foto")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Foto foto;
	
}
