package backend.JDA.servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import backend.JDA.modelo.dto.PaymentIntentDTO;

@Service
public class PaymentServiceImpl implements IPaymentService {
	
	@Value("${stripe.key.secret}")
	String secretKey;
	
	public PaymentIntent paymentIntent(PaymentIntentDTO paymentIntentDTO) throws StripeException {
		Stripe.apiKey = secretKey;		
		Map<String, Object> params = new HashMap<String, Object>();
		ArrayList payment_method_types = new ArrayList<>();
		
		payment_method_types.add("card");
		
		params.put("amount", paymentIntentDTO.getAmount());
		params.put("currency", paymentIntentDTO.getCurrency());
		params.put("description", paymentIntentDTO.getDescription());
		params.put("payment_method_types", payment_method_types);
		
		return PaymentIntent.create(params);
		
	}
	
	public PaymentIntent confirm(String id) throws StripeException {
		Stripe.apiKey = secretKey;
		PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("payment_method_types", "pm_card_visa");
		paymentIntent.confirm(params);
		
		return paymentIntent;
		
	}
	
	public PaymentIntent cancel(String id) throws StripeException {
		Stripe.apiKey = secretKey;
		PaymentIntent paymentIntent = PaymentIntent.retrieve(id);

		paymentIntent.cancel();
		
		return paymentIntent;
		
	}
	
}
