package backend.JDA.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@DiscriminatorValue("CA")
public class ClienteAnonimo extends Cliente{
	
	@Column(name = "fechaCreado")
	private LocalDateTime fechaCreado;
	@Column(name = "fechaExpiracion")
	private LocalDateTime fechaExpiracion;
	
}
