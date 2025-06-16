package backend.JDA.controladores;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	@GetMapping("/mandarMensaje")
	public ResponseEntity<String> chat(@RequestParam String message) {
		
		String chatResponse = servicioChatbot.chat(message);
			
		return ResponseEntity.ok(chatResponse);
		
	}
	
	
	
}
