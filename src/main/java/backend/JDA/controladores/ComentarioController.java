package backend.JDA.controladores;

import backend.JDA.modelo.Comentario;
import backend.JDA.modelo.ComidaPK;
import backend.JDA.modelo.dto.ComentarioDTO;
import backend.JDA.modelo.dto.ComentarioResponseDTO;
import backend.JDA.servicios.IServicioComentario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/comentarios")
@CrossOrigin(origins = "http://localhost:4200")
public class ComentarioController {

    @Autowired
    private IServicioComentario servicioComentario;

    @PostMapping("/crear")
    @Operation(
            summary = "Crear un nuevo comentario",
            description = "Permite a un usuario registrado o admin crear un comentario.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentario creado correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado o datos inválidos")
    })
    public ResponseEntity<ComentarioResponseDTO> crearComentario(@Valid @RequestBody ComentarioDTO comentario, @RequestParam String comida,
                                                                 @RequestParam String restaurante) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        ComidaPK comidapk = new ComidaPK(comida, restaurante);
        Optional<ComentarioResponseDTO> creado = servicioComentario.crearComentario(comentario, email, comidapk);

        return creado.map(ResponseEntity::ok).orElseGet(() -> (ResponseEntity<ComentarioResponseDTO>) ResponseEntity.status(HttpStatus.FORBIDDEN));
    }

    @DeleteMapping("/eliminar")
    @Operation(
            summary = "Eliminar comentario",
            description = "Permite a un ADMIN eliminar un comentario.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentario eliminado correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    public ResponseEntity<String> eliminarComentario(@RequestParam Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean eliminado = servicioComentario.eliminarComentario(id, email);

        return eliminado
                ? ResponseEntity.ok("Comentario eliminado")
                : ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado");
    }

    @GetMapping("/lista")
    @Operation(summary = "Obtener comentarios por comida", description = "Devuelve todos los comentarios asociados a una comida específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentarios obtenidos correctamente"),
            @ApiResponse(responseCode = "404", description = "Comida no encontrada")
    })
    public ResponseEntity<List<ComentarioResponseDTO>> obtenerComentariosPorComida(
            @RequestParam String comida,
            @RequestParam String restaurante) {

        List<ComentarioResponseDTO> comentarios = servicioComentario.obtenerComentariosPorComida(comida, restaurante);

        return ResponseEntity.ok(comentarios);
    }

    @GetMapping("/promedio")
    @Operation(
            summary = "Obtener promedio de valoraciones de una comida",
            description = "Devuelve el promedio de valoraciones como un número entero"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promedio obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "Comida no encontrada")
    })
    public ResponseEntity<Integer> obtenerPromedioValoracion(@RequestParam String comida,@RequestParam String restaurante) {
        int promedio = servicioComentario.obtenerPromedioValoracion(comida, restaurante);
        return ResponseEntity.ok(promedio);
    }



}
