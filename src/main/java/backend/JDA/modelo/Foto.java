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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name = "foto_id")
	private Long id;

	@Column(name = "url")
	private String url;
	
	private LocalDate fecha;
}
