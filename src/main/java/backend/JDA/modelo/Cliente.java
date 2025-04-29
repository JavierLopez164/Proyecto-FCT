package backend.JDA.modelo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Cliente {

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "pk_cliente")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
}
