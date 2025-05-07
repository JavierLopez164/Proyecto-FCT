package backend.JDA.modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

@Entity
@DiscriminatorValue("CR")
public class ClienteRegistrado extends Cliente{
	
	
	
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="foto_id")
	private Foto foto;
	
}
