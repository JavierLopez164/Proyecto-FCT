package backend.JDA.controladores;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import backend.JDA.modelo.Comida;
import backend.JDA.modelo.ComidaPK;
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
            description = "Permite a un admin crear una comida."
            //security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comida creada correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado o datos inválidos")
    })
	public ResponseEntity<String> crearComida( @RequestBody Comida comida){
		System.out.println("Creando comida" + comida);
		boolean creada = servicioComida.insert(comida);
		return creada ? ResponseEntity.ok("Comida creada") :
			ResponseEntity.status(HttpStatus.NOT_FOUND).body("No autorizado o datos inválidos");
	}
	
	@PostMapping("/actualizar")
    @Operation(
            summary = "Actualizar una comida",
            description = "Permite a un admin actualizar una comida."
            //security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comida creada correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado o datos inválidos")
    })
	public ResponseEntity<String> actualizarComida(@Valid @RequestBody Comida comida){
		System.out.println("Actualizando comida" + comida);
		boolean creada = servicioComida.update(comida);
		return creada ? ResponseEntity.ok("Comida actualizada") :
			ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado o datos inválidos");
	}
	
	@DeleteMapping("/eliminar")
    @Operation(
            summary = "Eliminar una comida",
            description = "Permite a un admin eliminar una comida."
            //security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comida creada correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado o datos inválidos")
    })
	public ResponseEntity<String> eliminarComida(@Valid @RequestBody ComidaPK comidaPK){
		System.out.println("Eliminando comida" + comidaPK);
		boolean creada = servicioComida.delete(comidaPK);
		return creada ? ResponseEntity.ok("Comida eliminada") :
			ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado o datos inválidos");
	}
	
	@GetMapping("/listarComidas")
    @Operation(
            summary = "Listar todas las comidas",
            description = "Permite a un admin listar todas las comidas."
            //security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comidas listadas correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado o datos inválidos")
    })
	public ResponseEntity<List<Comida>> listarTodasLasComidas(){
		List<Comida> comidas = servicioComida.findAll();
		return ResponseEntity.ok(comidas);
	}
	
	@GetMapping("/listarComidaPorId")
    @Operation(
            summary = "Obtener una comida con su id",
            description = "Permite a un admin obtener una comida por su id."
            //security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comidas listadas correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado o datos inválidos")
    })
	public ResponseEntity<Comida> obtenerUnaComidaPorId(@Valid @RequestBody ComidaPK comidaPK){
		Optional<Comida> comida = servicioComida.findById(comidaPK);
		ResponseEntity<Comida> response;

        response = comida.map(ResponseEntity::ok).orElseGet(() -> (ResponseEntity<Comida>) ResponseEntity.status(HttpStatus.FORBIDDEN));
		
		return response;
		
	}

	@GetMapping("/obtenerComidasDeUnRestaurante")
	@Operation(
			summary = "Obtener las comidas de un restaurante",
			description = "Permite a un admin obtener las comidas de un restaurante."
			//security = @SecurityRequirement(name = "bearerAuth")
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Comidas listadas correctamente"),
			@ApiResponse(responseCode = "403", description = "No autorizado o datos inválidos")
	})
	public ResponseEntity<List<Comida>> obtenerComidasDeUnRestaurante(@RequestAttribute String restaurante){
		List<Comida> comidas = servicioComida.obtenerComidasDeUnRestaurante(restaurante);
		return ResponseEntity.ok(comidas);
	}
	/*
	@PostMapping("/cambiarDescripcion")
    @Operation(
            summary = "Cambiar la descripcion de una comida",
            description = "Permite a un admin cambiar la descripcion de una comida."
            //security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Descripcion de la comida cambiada correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado o datos inválidos")
    })
	public ResponseEntity<String> cambiarDescripcion(@Valid @RequestBody ComidaPK comidaPK, @RequestAttribute String descripcion){
		System.out.println("Actualizando descripcion de la comida" + comidaPK);
		boolean creada = servicioComida.cambiarDescripcion(comidaPK, descripcion);
		return creada ? ResponseEntity.ok("Comida actualizada") :
			ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado o datos inválidos");
	}
	
	@PostMapping("/cambiarPrecio")
    @Operation(
            summary = "Cambiar el precio de una comida",
            description = "Permite a un admin cambiar el precio de una comida."
            //security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Precio de la comida cambiada correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado o datos inválidos")
    })
	public ResponseEntity<String> cambiarPrecio(@Valid @RequestBody ComidaPK comidaPK, @RequestAttribute float precio){
		
		System.out.println("Actualizando descripcion de la comida" + comidaPK);
		boolean creada = servicioComida.cambiarPrecio(comidaPK, precio);
		return creada ? ResponseEntity.ok("Comida actualizada") :
			ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado o datos inválidos");
	}
	
	@PostMapping("/cambiarValoracion")
    @Operation(
            summary = "Cambiar la valoracion de una comida",
            description = "Permite a un admin cambiar la valoracion de una comida."
            //security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valoracion de la comida cambiada correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado o datos inválidos")
    })
	public ResponseEntity<String> cambiarValoracion(@Valid @RequestBody ComidaPK comidaPK, @RequestAttribute int valoracion){
		
		System.out.println("Actualizando descripcion de la comida" + comidaPK);
		boolean creada = servicioComida.cambiarValoracion(comidaPK, valoracion);
		return creada ? ResponseEntity.ok("Comida actualizada") :
			ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado o datos inválidos");
	}
	*/
	
}