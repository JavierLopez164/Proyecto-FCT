package backend.JDA.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import backend.JDA.modelo.dto.PaymentIntentDTO;
import backend.JDA.servicios.IPaymentService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("api/stripe")
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {
	
	@Autowired
	private IPaymentService paymentService;
	
	@PostMapping("/paymentintent")
	public ResponseEntity<String> payment(@RequestBody PaymentIntentDTO paymentIntentDTO) throws StripeException{
		PaymentIntent paymentIntent = paymentService.paymentIntent(paymentIntentDTO);
		String paymentStr = paymentIntent.toJson();
		return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
		
	}
	
	@PostMapping("/confirm/{id}")
	public ResponseEntity<String> confirm(@PathVariable("id") String id) throws StripeException{
		PaymentIntent paymentIntent = paymentService.confirm(id);
		String paymentStr = paymentIntent.toJson();
		return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
	}
	
	@PostMapping("/cancel/{id}")
	public ResponseEntity<String> cancel(@PathVariable("id") String id) throws StripeException{
		PaymentIntent paymentIntent = paymentService.cancel(id);
		String paymentStr = paymentIntent.toJson();
		return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
	}
	
}
