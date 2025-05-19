package backend.JDA;

import backend.JDA.modelo.Comida;
import backend.JDA.modelo.Sabor;
import backend.JDA.servicios.IServicioComida;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

@Component
@Order(1)
public class CargarFicheroJson implements CommandLineRunner {

	@Autowired
	private IServicioComida servicioComida;

	@Override
	public void run(String... args) {
		try (Reader reader = new FileReader("Comidas.json")) {

			Gson gson = new GsonBuilder()
					.registerTypeAdapter(new TypeToken<List<Sabor>>() {}.getType(), new SaborListDeserializer())
					.create();

			Type listType = new TypeToken<List<Comida>>() {}.getType();
			List<Comida> comidas = gson.fromJson(reader, listType);

			System.out.println("Cargando comidas desde JSON...");
			for (Comida comida : comidas) {
				servicioComida.insert(comida);
			}

		} catch (Exception e) {
			System.err.println("Error cargando el fichero de comidas: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
