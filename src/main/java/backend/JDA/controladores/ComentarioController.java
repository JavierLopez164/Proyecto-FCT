package backend.JDA.controladores;

import backend.JDA.modelo.Comentario;
import backend.JDA.servicios.IServicioComentario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comentarios")
@CrossOrigin(origins = "http://localhost:4200")
public class ComentarioController {

    @Autowired
    private IServicioComentario servicioComentario;

    @PostMapping("/crear")
    @Operation(
            summary = "Crear un nuevo comentario",
            description = "Permite a un usuario registrado o admin crear un comentario."
            //security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentario creado correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado o datos inválidos")
    })
    public ResponseEntity<String> crearComentario(@Valid @RequestBody Comentario comentario, @RequestParam String cliente, @RequestParam String comida) {
        System.out.println("Creando comentario" + comentario + ", ++" + cliente + ", " + comida);
        boolean creado = servicioComentario.crearComentario(comentario, cliente, comida);
        return creado ? ResponseEntity.ok("Comentario creado") :
                ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado o datos inválidos");
    }

    @DeleteMapping("/eliminar")
    @Operation(
            summary = "Eliminar comentario",
            description = "Permite a un ADMIN eliminar un comentario."
            //security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentario eliminado correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    public ResponseEntity<String> eliminarComentario(
            @RequestParam Long id,
            @RequestParam String email) {
        boolean eliminado = servicioComentario.eliminarComentario(id, email);
        return eliminado ? ResponseEntity.ok("Comentario eliminado") :
                ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado");
    }

    @GetMapping("/lista")
    @Operation(
            summary = "Obtener comentarios por comida",
            description = "Devuelve todos los comentarios asociados a una comida específica."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentarios obtenidos correctamente"),
            @ApiResponse(responseCode = "404", description = "Comida no encontrada")
    })
    public ResponseEntity<List<Comentario>> obtenerComentariosPorComida(@RequestParam String nombreComida) {
        List<Comentario> comentarios = servicioComentario.obtenerComentariosPorComida(nombreComida);
        return ResponseEntity.ok(comentarios);
    }
    
    
    
}
