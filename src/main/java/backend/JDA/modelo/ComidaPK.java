package backend.JDA.modelo;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ComidaPK implements Serializable{

    private String nComida;
    private String nRestaurante;

}