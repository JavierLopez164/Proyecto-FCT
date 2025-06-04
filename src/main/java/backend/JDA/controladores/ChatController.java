package backend.JDA.controladores;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.JDA.servicios.IServicioChatbot;

@RestController
@RequestMapping("api/chatbot")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatController {
	
	@Autowired
	private IServicioChatbot servicioChatbot;
	
	//Envias un mensaje y openai te devuelve la respuesta
	@PostMapping("/mandarMensaje")
	public ResponseEntity<String> chat(@RequestParam String message) {
		
		/*if(message.contains("pedido")) {
			System.out.println("Ahora me pongo con el pedido");
			return ResponseEntity.ok("Ahora me pongo con el pedido");
		}
		else {*/
			String prompt = "Eres un asistente virtual amigable y eficiente de un restaurante. Tu objetivo es ayudar a los clientes respondiendo preguntas frecuentes y guiandolos en todo lo que necesiten"
				+ "relacionado con el restaurante. Puedes ayudar con temas como:"
				+ "-Platos disponibles, en el cual le tienes que decir que acceda a la ventana de pedidos y ahi seleccionar los platos que quiera del restaurante en el que se encuentre"
				+ "-Metodos de pago aceptados, el cual es visa o mastercard"
				+ "-Atencion a reclamos o sugerencias, en las cuales tienes que decir que se ponga en contacto con el personal del restaurante, ya que no hay ninguna otra opcion de hacerlo"
				+ "Siempre responde de forma cortes, clara y util. Si no tienes una respuesta, sugiere amablemente contactar al personal del restaurante.";
			
			String chatResponse = servicioChatbot.chat(prompt, message);
			
			return ResponseEntity.ok(chatResponse);
		//}
		
	}
	
	
	
}
