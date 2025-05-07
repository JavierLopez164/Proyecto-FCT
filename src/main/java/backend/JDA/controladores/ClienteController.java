package backend.JDA.controladores;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.JDA.config.JwtAuthentication;
import backend.JDA.modelo.Cliente;

import backend.JDA.servicios.IServicioCliente;
import backend.JDA.servicios.ServicioCliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("api/clientes")
@CrossOrigin(origins = "http://localhost:4200")
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
		Optional<Cliente> c = servicioCliente.findById(email);
		Map<String, String> resJson = new HashMap<>();
		String token;

		if (c.isPresent() && passwordEncoder.matches(password, c.get().getContrasenia())) {
			token = jwtAuthtenticationConfig.getJWTToken(c.get().getNombre(), c.get().getRol());
			resJson.put("token", token);
			resJson.put("mensaje", "Login exitoso");
			return ResponseEntity.ok(resJson);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Map.of("mensaje", "Email o contraseña incorrectos"));
		}
	}

	@PostMapping("/register")
	@Operation(summary = "Crear un nuevo cliente registrado")
	@ApiResponse(responseCode = "200", description = "Cliente registrado creado exitosamente")
	@ApiResponse(responseCode = "400", description = "Error al crear el cliente registrado")

	public ResponseEntity<Cliente> crearClienteRegistrado( @RequestBody Cliente client) {
		ResponseEntity<Cliente> response;
		if (servicioCliente.registrarCliente(client)) {
			response = ResponseEntity.ok(client);
		} else {
			response = ResponseEntity.badRequest().build();
		}
		return response;
	}

	@GetMapping("/mensaje")
	public List<String> getMensajesToken(){

		return Arrays.asList(("Paco"),("Pedro"), ("Juan"));
	
	
	}
}

