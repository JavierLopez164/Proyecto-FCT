package backend.JDA.controladores;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import backend.JDA.servicios.IServicioAdministrador;
import backend.JDA.modelo.Administrador;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/administradores")
@Tag(name = "Administrador", description = "Operaciones relacionadas con administradores")
public class AdministradorController {

    @Autowired
    private IServicioAdministrador servicioAdministrador;

    @PostMapping("/register")
    @Operation(
            summary = "Crear un nuevo administrador",
            description = "Crea un nuevo administrador en la base de datos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Administrador creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error al crear el administrador")
            }
    )
    public ResponseEntity<String> crearAdministrador(@RequestBody Administrador administrador) {
        return servicioAdministrador.insert(administrador)
                ? ResponseEntity.ok("Administrador creado exitosamente")
                : ResponseEntity.badRequest().body("Error al crear el administrador");
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener administrador por ID (email)",
            description = "Devuelve el administrador que coincide con el ID (email) proporcionado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Administrador encontrado"),
                    @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
            }
    )
    public ResponseEntity<?> obtenerAdministrador(@PathVariable String id) {
        Optional<Administrador> admin = servicioAdministrador.findById(id);
        return admin.isPresent()
                ? ResponseEntity.ok(admin)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/")
    @Operation(
            summary = "Actualizar un administrador",
            description = "Actualiza los datos de un administrador existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Administrador actualizado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error al actualizar el administrador")
            }
    )
    public ResponseEntity<String> actualizarAdministrador(@RequestBody Administrador administrador) {
        return servicioAdministrador.update(administrador)
                ? ResponseEntity.ok("Administrador actualizado exitosamente")
                : ResponseEntity.badRequest().body("Error al actualizar el administrador");
    }

    @DeleteMapping("/{email}")
    @Operation(
            summary = "Eliminar un administrador",
            description = "Elimina el administrador que coincide con el email proporcionado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Administrador eliminado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error al eliminar el administrador")
            }
    )
    public ResponseEntity<String> eliminarAdministrador(@PathVariable String email) {
        return servicioAdministrador.delete(email)
                ? ResponseEntity.ok("Administrador eliminado exitosamente")
                : ResponseEntity.badRequest().body("Error al eliminar el administrador");
    }

    @Operation(
            summary = "Login de administrador",
            description = "Verifica las credenciales de un administrador y devuelve su información si son correctas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login exitoso"),
                    @ApiResponse(responseCode = "400", description = "Credenciales incorrectas")
            }
    )   
    @GetMapping("/login")
  	public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
      	
  		return ResponseEntity.ok( servicioAdministrador.administradorCoincidente(username,password));
  		
  	}
}
