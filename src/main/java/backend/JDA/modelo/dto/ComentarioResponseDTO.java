package backend.JDA.modelo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ComentarioResponseDTO {
    @NotBlank
    private Long id;
    @NotBlank
    private String clienteEmail;
    @NotBlank(message = "El contenido no puede estar vacío")
    private String contenido;
    @Min(value = 1, message = "La valoración mínima es 1")
    @Max(value = 5, message = "La valoración máxima es 5")
    private int valoracion;
    private Boolean destacado;
}
