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

import backend.JDA.config.DtoConverter;
import backend.JDA.modelo.Cliente;
import backend.JDA.modelo.Comida;
import backend.JDA.modelo.ComidaPK;
import backend.JDA.modelo.Foto;
import backend.JDA.modelo.dto.ClienteFotoDto;
import backend.JDA.modelo.dto.ComidaFotoDto;
import backend.JDA.repositorios.ClienteRepositorio;
import backend.JDA.repositorios.ComidaRepositorio;
import backend.JDA.repositorios.FotoRepositorio;

@Service
public class ServicioFotoImpl implements IServicioFoto {

	@Autowired
	private FotoRepositorio fotoDAO;
	@Autowired 
	private ClienteRepositorio clienteDao;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired 
	private DtoConverter dtoConverter;
	@Autowired 
	private ComidaRepositorio comidaDao;
	private String cloudName="dmljwxole";
	private String uploadPreset="ml_default";
	private String url= "https://api.cloudinary.com/v1_1/" + cloudName + "/image/upload";


	//El MultipartFile es una interfaz que representa el archivo cuando se hace un Request
	public Optional<ClienteFotoDto> subirImagenACloudFotoPerfil(MultipartFile imagenFichero, String email) {
	    Optional<Cliente> client = clienteDao.findById(email);
	    // Leer el archivo y convertirlo a Base64
	    Optional<ClienteFotoDto> cliFoto=Optional.empty();
	  if(client.isPresent()) {

	        byte[] bytes = null;
			try {
				bytes = imagenFichero.getBytes();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        String base64Image = Base64.getEncoder().encodeToString(bytes);
		    // Crear body de la request
		    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		    body.add("file",  "data:image/png;base64," + base64Image); // Enviar archivo en bytes
		    body.add("upload_preset", uploadPreset);
		    body.add("folder", "usuarios/fotoperfil/" + email);

		    // Configurar cabeceras
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		    HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

		    try {
		        ResponseEntity<Map> response = restTemplate.postForEntity(url ,request, Map.class);
		        if (response.getBody() != null && response.getBody().containsKey("secure_url")) {
		            Foto f = fotoDAO.save(Foto.builder()
		                .fecha(LocalDate.now())
		                .imagenUrl(response.getBody().get("secure_url").toString())
		                .build());
		            client.get().setFotoPerfil(f);
		            clienteDao.save(client.get());
		            cliFoto=client.map(c->dtoConverter.map(c, ClienteFotoDto.class));
		            System.out.println(cliFoto);
		            System.out.println("Se hizo la petición Se ha subido a Cloudinary tu foto de perfil");
		        } else {
		            System.out.println("Error: Respuesta inesperada de Cloudinary");
		        }
		    } catch (Exception e) {
		        System.out.println("Error al subir imagen: " + e.getMessage());
		    }

	  }else
		  System.out.println("Cliente no encontrado");
	    return cliFoto;
	}

	@Override
	public Optional<ComidaFotoDto> subirImagenACloudComida(MultipartFile imagenFichero,ComidaPK comidaPK,String email)  {
		// TODO Auto-generated method stub
		 Optional<Cliente> client = clienteDao.findById(email);
		 Optional<ComidaFotoDto>comidaFoto=Optional.empty();
		 Optional<Comida>comida=comidaDao.findById(comidaPK);
		  if(client.isPresent()) {
			  if(comida.isPresent()) {
		        byte[] bytes = null;
				try {
					bytes = imagenFichero.getBytes();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        String base64Image = Base64.getEncoder().encodeToString(bytes);
			   
			    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			    body.add("file",  "data:image/jpeg;base64," + base64Image); // Enviar archivo en bytes
			    body.add("upload_preset", uploadPreset);
			    body.add("folder", "usuarios/comida/" + email);
		
			   
			    HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			    HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

			    try {
			        ResponseEntity<Map> response = restTemplate.postForEntity(url ,request, Map.class);
			        if (response.getBody() != null && response.getBody().containsKey("secure_url")) {
			            Foto f = fotoDAO.save(Foto.builder()
			                .fecha(LocalDate.now())
			                .imagenUrl(response.getBody().get("secure_url").toString())
			                .build());
			            comida.get().setFoto(f);
			            comidaDao.save(comida.get());
			            comidaFoto=comida.map(c->dtoConverter.map(c, ComidaFotoDto.class));
			            System.out.println("Se hizo la petición Se ha subido a Cloudinary tu comida");
			        } else {
			            System.out.println("Error: Respuesta inesperada de Cloudinary");
			        }
			    } catch (Exception e) {
			        System.out.println("Error al subir imagen: " + e.getMessage());
			    }
			    System.out.println("Comida no encontrada");
			  }
			  System.out.println("Cliente no encontrado");
		  }
		
		return comidaFoto;
	}
	
	
	@Override
	public boolean update(Foto foto) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(fotoDAO.existsById(foto.getFotoId())) {
			fotoDAO.save(foto);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean delete(int id) {
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
	public Optional<Foto> findById(int id) {
		// TODO Auto-generated method stub
		return fotoDAO.findById(id);
	}

}
