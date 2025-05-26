package backend.JDA.modelo.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PedidoListadoDTO {
    private String id;
    private String emailCliente;
    private List<ItemDTO> items;
    private LocalDate fechaCreacion;
    private LocalDate fechaExpiracion;
    private String restaurante;
    private int cantidadFinal;
}