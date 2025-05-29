package backend.JDA.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;

import backend.JDA.modelo.dto.PaymentIntentDTO;

@Service
public class PaymentServiceImpl implements IPaymentService {

	@Value("${stripe.key.secret}")
	String secretKey;

	@Override
	public PaymentIntent paymentIntent(PaymentIntentDTO dto) throws StripeException {
		Stripe.apiKey = secretKey;
		String currency = dto.getCurrency() != null ? dto.getCurrency().name().toLowerCase() : "eur";
		System.out.println("Stripe API Key: " + dto);

		Map<String, Object> customerParams = new HashMap<>();
		customerParams.put("description", "Cliente de prueba");
		Customer customer = Customer.create(customerParams);

		PaymentMethod paymentMethod = PaymentMethod.retrieve(dto.getPaymentMethodId());
		Map<String, Object> attachParams = new HashMap<>();
		attachParams.put("customer", customer.getId());
		paymentMethod.attach(attachParams);

		Map<String, Object> params = new HashMap<>();
		params.put("amount", dto.getAmount());
		params.put("currency", currency);
		params.put("description", dto.getDescription());
		params.put("payment_method", dto.getPaymentMethodId());
		params.put("customer", customer.getId());
		params.put("confirm", true);
		params.put("return_url", "http://localhost:4200/success");
		return PaymentIntent.create(params);
	}

	@Override
	public PaymentIntent confirm(String id) throws StripeException {
		Stripe.apiKey = secretKey;
		PaymentIntent paymentIntent = PaymentIntent.retrieve(id);

		Map<String, Object> params = new HashMap<>();
		params.put("payment_method_types", "pm_card_visa");

		paymentIntent.confirm(params);
		return paymentIntent;
	}

	@Override
	public PaymentIntent cancel(String id) throws StripeException {
		Stripe.apiKey = secretKey;
		PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
		paymentIntent.cancel();
		return paymentIntent;
	}
}
