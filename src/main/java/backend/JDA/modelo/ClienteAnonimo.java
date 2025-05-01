package backend.JDA.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@DiscriminatorValue("CA")
public class ClienteAnonimo extends Cliente {

	@Column(name = "fechaCreado")
	private LocalDateTime fechaCreado;

	@Column(name = "fechaExpiracion")
	private LocalDateTime fechaExpiracion;
}
