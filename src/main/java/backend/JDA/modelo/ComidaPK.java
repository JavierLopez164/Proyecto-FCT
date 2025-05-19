package backend.JDA.modelo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComidaPK implements Serializable{
	
	private String nComida;
	private String nRestaurante;
	
}
