package backend.JDA.controladores;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.JDA.config.JwtAuthentication;
import backend.JDA.modelo.Cliente;
import backend.JDA.modelo.ClienteRegistrado;
import backend.JDA.servicios.IServicioCliente;
import backend.JDA.servicios.ServicioCliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("api/clientes")

public class ClienteController {

 
	 @Autowired
	    private IServicioCliente servicioCliente;
	@Autowired
		JwtAuthentication jwtAuthtenticationConfig;
	 @Autowired
	    private BCryptPasswordEncoder passwordEncoder;
	
	 @GetMapping("/login")
	 @Operation(summary = "Iniciar sesión con email y contraseña")
	    @ApiResponse(responseCode = "200", description = "Login exitoso")
	    @ApiResponse(responseCode = "400", description = "Credenciales incorrectas")
	  
	  	public ResponseEntity<Map<String,String>> login(@RequestParam String email, @RequestParam String password) {
	      	Optional<Cliente> c=servicioCliente.findById(email);
	    	ResponseEntity<Map<String,String>> response = null;
	    	  Map<String, String> resJson = new HashMap<>();
	    	String token;
	
		 	if(c.isPresent() && passwordEncoder.matches(password, c.get().getContrasenia()))
		 	{
		 		token=jwtAuthtenticationConfig.getJWTToken(c.get().getNombre(), c.get().getRol());
		 		resJson.put("token", token);
		 		resJson.put("mensaje", "Login exitoso");
		 		response = new ResponseEntity<>(resJson,HttpStatus.OK);
		 	
		 	}else
		 		response = new ResponseEntity<>(Map.of("mensaje","Email o contraseña incorrectos"),HttpStatus.NOT_FOUND);
		 
		 
	  		return response;
	  		
	  	}  
	 @PostMapping("/register")
	 @Operation(summary = "Crear un nuevo cliente registrado")
	    @ApiResponse(responseCode = "200", description = "Cliente registrado creado exitosamente")
	    @ApiResponse(responseCode = "400", description = "Error al crear el cliente registrado")
	  
	    public ResponseEntity<String> crearClienteRegistrado(@RequestBody Cliente client) {
	        ResponseEntity<String> response;
	        if (servicioCliente.registrarCliente(client)) {
	            response = ResponseEntity.ok("Cliente registrado creado exitosamente");
	        } else {
	            response = ResponseEntity.badRequest().body("Error al crear el cliente registrado");
	        }
	        return response;
	    }

	    @GetMapping("/mensaje")
	
	    @ApiResponse(responseCode = "200", description = "Mensaje creado exitosamente")
	    @ApiResponse(responseCode = "400", description = "Error al crear el mensaje")
	   
	    public List<String> getMensajesToken(){

	        return Arrays.asList(("Paco"),("Pedro"), ("Juan"));
	        
	        
	    }
}
