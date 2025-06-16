package backend.JDA.controladores;

import java.util.List;
import java.util.Optional;

import backend.JDA.modelo.*;
import backend.JDA.modelo.dto.ComidaUpdateDto;
import backend.JDA.servicios.IServicioCliente;
import backend.JDA.servicios.IServicioPedido;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import backend.JDA.modelo.dto.ComidaGaleriaDto;
import backend.JDA.servicios.IServicioComida;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/comida")
@CrossOrigin(origins = "http://localhost:4200")
public class ComidaController {

	@Autowired
	private IServicioComida servicioComida;

	@Autowired
	private IServicioCliente servicioCliente;
	@Autowired
	private IServicioPedido servicioPedido;

	@PostMapping("/crear")
	@Operation(
			summary = "Crear una nueva comida",
			description = "Permite a un admin crear una comida.",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Comida creada correctamente"),
			@ApiResponse(responseCode = "403", description = "No autorizado o datos inválidos")
	})
	public ResponseEntity<?> crearComida(@Valid @RequestBody Comida comida) {
		boolean creada = servicioComida.insert(comida);
		return creada
				? ResponseEntity.ok(comida) // <-- Retorna el objeto creado
				: ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado o datos inválidos");
	}

	@PostMapping("/actualizar")
	@Operation(
			summary = "Actualizar una comida",
			description = "Permite a un admin actualizar una comida.",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	public ResponseEntity<?> actualizarComida(@Valid @RequestBody ComidaUpdateDto comida) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Cliente> clienteOpt = servicioCliente.findById(email);
		ResponseEntity<?> response;

		if (clienteOpt.isEmpty() || !clienteOpt.get().getRol().equals(Rol.ROLE_ADMIN)) {
			 response = ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body("No tienes permisos para realizar esta acción");
		}else{
			Optional<Comida> actualizada = servicioComida.update(comida);
			if(actualizada.isPresent()){
				response = ResponseEntity.ok(comida);
			}else{
				response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("La comida no existe");
			}

		}
		return response;
	}


	@DeleteMapping("/eliminar")
	@Operation(
			summary = "Eliminar una comida",
			description = "Permite a un admin eliminar una comida.",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	public ResponseEntity<?> eliminarComida(@RequestParam String comida,
											@RequestParam String restaurante) {

		ComidaPK comidaPK = ComidaPK.builder()
				.nComida(comida)
				.nRestaurante(restaurante)
				.build();

		boolean eliminada = servicioComida.delete(comidaPK);
		return eliminada
				? ResponseEntity.ok("Comida eliminada")
				: ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado o datos inválidos");
	}

	@PutMapping("/comida/alternar-ocultar")
	public ResponseEntity<String> alternarOcultarComida(@RequestBody ComidaPK pk) {
		boolean resultado = servicioComida.alternarOcultar(pk);
		if (resultado) {
			return ResponseEntity.ok("Visibilidad de la comida actualizada correctamente.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comida no encontrada.");
		}
	}


	@GetMapping("/listarComidas")
	@Operation(
			summary = "Listar todas las comidas",
			description = "Permite listar todas las comidas disponibles.",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	public ResponseEntity<List<Comida>> listarTodasLasComidas() {
		List<Comida> comidas = servicioComida.findAll();
		return ResponseEntity.ok(comidas);
	}

	@PostMapping("/obtenerPorId")
	@Operation(
			summary = "Obtener una comida por ID",
			description = "Permite obtener una comida específica usando su ID.",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	public ResponseEntity<?> obtenerUnaComidaPorId(
			@RequestParam String comida,
			@RequestParam String restaurante) {

		ComidaPK comidaPK = ComidaPK.builder()
				.nComida(comida)
				.nRestaurante(restaurante)
				.build();

		Optional<Comida> comidaOpt = servicioComida.findById(comidaPK);
		return comidaOpt.isPresent()
				? ResponseEntity.ok(comidaOpt.get())
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comida no encontrada");
	}

	@PostMapping("/obtenerPorRestaurante")
	@Operation(
			summary = "Obtener comidas de un restaurante",
			description = "Permite obtener todas las comidas asociadas a un restaurante.",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	public ResponseEntity<List<Comida>> obtenerComidasDeUnRestaurante(@RequestBody String restaurante) {
		List<Comida> comidas = servicioComida.obtenerComidasDeUnRestaurante(restaurante);
		return ResponseEntity.ok(comidas);
	}
	
	
	
	
	@GetMapping("/obtenerComidasDeUnRestaurantes")
	@Operation(
			summary = "Obtener las comidas de un restaurante pero con dto especifico para galeria",
			description = "Permite obtener las comidas de un restaurante.",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	public ResponseEntity<List<ComidaGaleriaDto>> obtenerComidasRestaurante(@RequestParam String restaurante) {
		
		return ResponseEntity.ok(servicioComida.obtenerTodasLasComidasDeUnRestaurantes(restaurante));
	}
	
	@GetMapping("/obtenerNombresRestaurante")
	@Operation(
			summary = "Obtener el nombre de todos los restaurantes",
			description = "Permite obtener todos los nombres de los restaurantes.",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	public ResponseEntity<List<String>> obtenerNombreTodosRestaurante() {
		
		return ResponseEntity.ok(servicioComida.obtenerTodosLosRestaurantes());
	}
	
}