package backend.JDA.servicios;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ServicioChatbotImpl implements IServicioChatbot{

	private final ChatClient chatClient;
	
	//Hay que iniciar el chat
	public ServicioChatbotImpl(ChatClient.Builder builder) {
		chatClient = builder.build();
	}
	
	//Envias un mensaje y openai te devuelve la respuesta
	@Override
	public String chat(String prompt, String message) {
		// TODO Auto-generated method stub
		return chatClient
				.prompt(prompt)
				.user(message)
				.call()
				.content();
	}

}
