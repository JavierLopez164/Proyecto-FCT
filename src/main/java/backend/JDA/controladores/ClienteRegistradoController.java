package backend.JDA.controladores;

import backend.JDA.servicios.IServicioClienteRegistrado;
import backend.JDA.modelo.ClienteRegistrado;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/clientes-registrados")
public class ClienteRegistradoController {

    @Autowired
    private IServicioClienteRegistrado servicioCliente;

    @Operation(summary = "Crear un nuevo cliente registrado")
    @ApiResponse(responseCode = "200", description = "Cliente registrado creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Error al crear el cliente registrado")
    @PostMapping("/")
    public ResponseEntity<String> crearClienteRegistrado(@RequestBody ClienteRegistrado clienteRegistrado) {
        ResponseEntity<String> response;
        if (servicioCliente.insert(clienteRegistrado)) {
            response = ResponseEntity.ok("Cliente registrado creado exitosamente");
        } else {
            response = ResponseEntity.badRequest().body("Error al crear el cliente registrado");
        }
        return response;
    }

    @Operation(summary = "Actualizar cliente registrado por ID")
    @ApiResponse(responseCode = "200", description = "Cliente registrado actualizado exitosamente")
    @ApiResponse(responseCode = "400", description = "Error al actualizar el cliente registrado")
    @PutMapping("/")
    public ResponseEntity<String> actualizarClienteRegistrado(@RequestBody ClienteRegistrado clienteRegistrado) {
        ResponseEntity<String> response;
        if (servicioCliente.update(clienteRegistrado)) {
            response = ResponseEntity.ok("Cliente registrado actualizado exitosamente");
        } else {
            response = ResponseEntity.badRequest().body("Error al actualizar el cliente registrado");
        }
        return response;
    }

    @Operation(summary = "Eliminar cliente registrado por ID")
    @ApiResponse(responseCode = "200", description = "Cliente registrado eliminado exitosamente")
    @ApiResponse(responseCode = "400", description = "Error al eliminar el cliente registrado")
    @DeleteMapping("/{email}/{contrasena}")
    public ResponseEntity<String> eliminarClienteRegistrado(@PathVariable String email, @PathVariable String contrasena) {
        ResponseEntity<String> response;
        if (servicioCliente.delete(email, contrasena)) {
            response = ResponseEntity.ok("Cliente registrado eliminado exitosamente");
        } else {
            response = ResponseEntity.badRequest().body("Error al eliminar el cliente registrado");
        }
        return response;
    }

    @Operation(summary = "Iniciar sesión con email y contraseña")
    @ApiResponse(responseCode = "200", description = "Login exitoso")
    @ApiResponse(responseCode = "400", description = "Credenciales incorrectas")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String contrasenia) {
        ResponseEntity<?> response;
        Optional<ClienteRegistrado> cliente = servicioCliente.login(email, contrasenia);
        if (cliente.isPresent()) {
            response = ResponseEntity.ok(cliente);
        } else {
            response = ResponseEntity.badRequest().body("Credenciales incorrectas");
        }
        return response;
    }
}
