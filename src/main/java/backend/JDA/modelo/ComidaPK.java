package backend.JDA.modelo;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
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
public class ComidaPK implements Serializable {
    @Column(name = "nComida", nullable = false)
    private String nComida;

    @Column(name = "nRestaurante", nullable = false)
    private String nRestaurante;
}
