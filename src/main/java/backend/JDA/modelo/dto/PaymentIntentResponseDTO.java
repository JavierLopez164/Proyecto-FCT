package backend.JDA.modelo.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class PaymentIntentResponseDTO {
    private String id;
    private String status;
    private String clientSecret;
}
