package backend.JDA.servicios;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import backend.JDA.modelo.Cliente;
import backend.JDA.modelo.Foto;
import backend.JDA.repositorios.ClienteRepositorio;
import backend.JDA.repositorios.FotoRepositorio;
/*
@Service
public class ServicioFotoImpl implements IServicioFoto {

	@Autowired
	private FotoRepositorio fotoDAO;
	@Autowired 
	private ClienteRepositorio clienteDao;
	@Autowired
	private RestTemplate restTemplate;
	
	private String cloudName="";
	private String uploadPreset="";

	
	//El MultipartFile es una interfaz que representa el archivo cuando se hace un Request 
	public boolean subirImagenACloud(MultipartFile imagenFichero,String email) throws IOException {
		String url = "https://api.cloudinary.com/v1_1/" + cloudName + "/image/upload";
		Optional<Cliente> client=clienteDao.findById(email);
		boolean exitoAlSubirFoto=false;
		
		if(client.isPresent()) {
	        // Crear body de la request 
			// Leer archivo y pasarlo en base 64=[A-Z a-z 0-9]
	        byte[] bytes = imagenFichero.getBytes();
	        String base64 = Base64.getEncoder().encodeToString(bytes);
	        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
	        body.add("file", "data:image/jpeg;base64," + base64); //La imagen en base64 para cloudinary sepa el contenido
	        body.add("upload_preset",uploadPreset); 
	        body.add("folder", "usuarios/"+email);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		    HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
		    ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
		    Foto f=fotoDAO.save(Foto.builder().fecha(LocalDate.now()).url(response.getBody().get("secure_url").toString()).build());
		    client.get().getFoto().add(f);
		    clienteDao.save(client.get());
		    exitoAlSubirFoto=true;
	
		}
		return exitoAlSubirFoto;
	  
	
	}
	
	@Override
	public boolean update(Foto foto) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(fotoDAO.existsById(foto.getId())) {
			fotoDAO.save(foto);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(fotoDAO.existsById(id)) {
			fotoDAO.deleteById(id);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public List<Foto> findAll() {
		// TODO Auto-generated method stub
		return (List<Foto>) fotoDAO.findAll();
	}

	@Override
	public Optional<Foto> findById(Long id) {
		// TODO Auto-generated method stub
		return fotoDAO.findById(id);
	}
	
}
*/