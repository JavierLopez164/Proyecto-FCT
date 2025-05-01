package backend.JDA.controladores;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.JDA.modelo.ClienteRegistrado;
import backend.JDA.servicios.IServicioCliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;



@RestController
@RequestMapping("api/clientes")
public class ClienteController {
	 @Autowired
	    private IServicioCliente servicioCliente;
	  @GetMapping("/login")
	 @Operation(summary = "Iniciar sesión con email y contraseña")
	    @ApiResponse(responseCode = "200", description = "Login exitoso")
	    @ApiResponse(responseCode = "400", description = "Credenciales incorrectas")
	  
	  	public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
	      	
	  		return ResponseEntity.ok( servicioCliente.login(username,password));
	  		
	  	}  
	 @PostMapping("/register")
	 @Operation(summary = "Crear un nuevo cliente registrado")
	    @ApiResponse(responseCode = "200", description = "Cliente registrado creado exitosamente")
	    @ApiResponse(responseCode = "400", description = "Error al crear el cliente registrado")
	  
	    public ResponseEntity<String> crearClienteRegistrado(@RequestBody ClienteRegistrado clienteRegistrado) {
	        ResponseEntity<String> response;
	        if (servicioCliente.registrarCliente(clienteRegistrado)) {
	            response = ResponseEntity.ok("Cliente registrado creado exitosamente");
	        } else {
	            response = ResponseEntity.badRequest().body("Error al crear el cliente registrado");
	        }
	        return response;
	    }

	    @GetMapping(value = "/mensaje")
	    @Operation(summary = "Crear mensaje")
	    @ApiResponse(responseCode = "200", description = "Mensaje creado exitosamente")
	    @ApiResponse(responseCode = "400", description = "Error al crear el mensaje")
	    public List<String> getMensajesToken(){

	        return Arrays.asList(("Paco"),("Pedro"), ("Juan"));
	    }
}
