package backend.JDA.modelo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Document(collection = "comida")
public class Comida implements Serializable{
	
	@EqualsAndHashCode.Include
	@Id
	//Nombre + restaurante
	private ComidaPK comidaPK;
	//@Column(name = "descripcion", length = 40)
	private String descripcion;
	//@Column(name = "precio")
	private float precio;
	//@Column(name = "sabor")
	//@Enumerated(EnumType.STRING)
	private Sabor sabor;
	//@Column(name = "valoracion")
	private int valoracion;
	//@Column(name = "alimentos")
	private List<String> alimentos;
	/*@JoinColumn(name = "foto")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Foto foto;*/
	
}
