package backend.JDA.modelo.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class ItemDTO {
    private String nombreComida;
    private int cantidad;
    private int precioUnitario;
}