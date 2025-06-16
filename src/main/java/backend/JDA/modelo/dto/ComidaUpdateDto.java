package backend.JDA.modelo.dto;

import backend.JDA.modelo.Sabor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ComidaUpdateDto {
    @NotBlank
    private String ncomida;
    @NotBlank
    private String restaurante;

    @Size(max = 200)
    private String description;

    @Min(0)
    private int price;

    @NotBlank
    private String category;

    private List<Sabor> attributes;

    private List<String> features;

    @Min(10)
    private int preparationTime;
}
