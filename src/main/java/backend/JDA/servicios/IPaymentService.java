package backend.JDA.servicios;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import backend.JDA.modelo.dto.PaymentIntentDTO;

public interface IPaymentService {
	
	public PaymentIntent paymentIntent(PaymentIntentDTO paymentIntentDTO) throws StripeException;
	
	public PaymentIntent confirm(String id) throws StripeException;
	
	public PaymentIntent cancel(String id) throws StripeException;
	
}
