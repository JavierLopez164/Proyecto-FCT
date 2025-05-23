package backend.JDA.controladores;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.JDA.modelo.Comida;
import backend.JDA.modelo.ComidaPK;
import backend.JDA.modelo.Foto;
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
	public ResponseEntity<?> actualizarComida(@Valid @RequestBody Comida comida) {
		boolean actualizada = servicioComida.update(comida);
		return actualizada
				? ResponseEntity.ok(comida)
				: ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado o datos inválidos");
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
	@GetMapping("/obtenerTodosLosRestaurantes")
	@Operation(
			summary = "Obtener las fotos de un restaurante",
			description = "Permite obtener las fotos de un restaurante.",
			security = @SecurityRequirement(name = "bearerAuth")
	)
	public ResponseEntity<List<Foto>> obtenerFotosRestaurante(@RequestParam String restaurante) {
		
		return ResponseEntity.ok(servicioComida.obtenerTodasLasFotosDeUnRestaurantes(restaurante));
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