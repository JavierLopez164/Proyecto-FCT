package backend.JDA.modelo.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class PaymentIntentDTO {
	public enum Currency{
		USD, EUR;
	}
	
	private String description;
	private int amount;
	private Currency currency;
	private String paymentMethodId;
	private String correo;
	
}
