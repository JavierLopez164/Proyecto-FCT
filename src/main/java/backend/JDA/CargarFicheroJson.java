package backend.JDA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

import backend.JDA.modelo.Comida;
import backend.JDA.servicios.IServicioComida;
import backend.JDA.servicios.ServicioComidaImpl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

//@Component
//@Order(value = 1)
public class CargarFicheroJson implements CommandLineRunner {
	
	@Autowired
	static IServicioComida servicioComida;

	@Override
	public void run(String... args) throws Exception {
//		// TODO Auto-generated method stub
//		List<Comida> comidas;
//		
//		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
//		
//		Gson gson = builder.create();
//		
//		Reader reader = new FileReader(new File("Comidas.json"));
//		
//		comidas = gson.fromJson(reader, List.class);
//		
//		reader.close();
//		
//		System.out.println("Cargando comidas del json");
//		
//		for (int i = 0; i < comidas.size(); i++) {
//			servicioComida.insert(comidas.get(i));
//		}
	}

}
