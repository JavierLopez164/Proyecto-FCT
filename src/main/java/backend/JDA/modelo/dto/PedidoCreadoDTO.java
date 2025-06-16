package backend.JDA.modelo.dto;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class PedidoCreadoDTO {
    private String id;
    private String emailCliente;
    private String restaurante;
	private boolean activo;
}

