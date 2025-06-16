package backend.JDA.modelo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopComidaDTO {
    private String nombreComida;
    private String restaurante;
    private Long cantidadTotal;
}

