package backend.JDA.modelo;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
public class Foto {
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Integer fotoId;

	@Column(name = "url")
	private String url;
	
	private LocalDate fecha;
	
	@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
	@JoinColumn(name = "pk_email")
	private Cliente cliente;
	
}
