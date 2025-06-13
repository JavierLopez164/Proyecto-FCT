package backend.JDA.servicios;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.JDA.repositorios.ComidaRepositorio;

@Service
public class ServicioChatbotImpl implements IServicioChatbot{

	private final ChatClient chatClient;
	
	//Hay que iniciar el chat
	private ServicioChatbotImpl(ChatClient.Builder builder) {
		chatClient = builder.build();
	}
	
	//Envias un mensaje y openai te devuelve la respuesta
	@Override
	public String chat(String message) {
		// TODO Auto-generated method stub
		String prompt;

		prompt = getPrompt(message);
		
		return chatClient
				.prompt(prompt)
				.user(message)
				.call()
				.content();
	}
	
	private String getPrompt(String message) {
		String prompt;
		
			prompt = "Eres un asistente virtual amigable y eficiente de un restaurante. Tu objetivo es ayudar a los clientes respondiendo preguntas frecuentes y guiandolos en todo lo que necesiten"
					+ "relacionado con el restaurante. Puedes ayudar con temas como:"
					+ "-Platos disponibles, en el cual le tienes que decir que acceda a la ventana de pedidos y ahi seleccionar los platos que quiera del restaurante en el que se encuentre"
					+ "-Metodos de pago aceptados, el cual es visa o mastercard"
					+ "-Atencion a reclamos o sugerencias, en las cuales tienes que decir que se ponga en contacto con el personal del restaurante, ya que no hay ninguna otra opcion de hacerlo"
					+ "Siempre responde de forma cortes, clara y util. Si no tienes una respuesta, sugiere amablemente contactar al personal del restaurante. Ademas, si detectas que lo que haya escrito "
					+ "el cliente no tiene nada que ver con los puntos que se te han especificado, directamente responde que no puedes ayudarle con esa operacion ya que no esta relacionado con el restaurante";
		
		return prompt;
		
	}

}
