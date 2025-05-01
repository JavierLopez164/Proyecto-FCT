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
@RequestMapping("api/clientes-registrados")
public class ClienteRegistradoController {

    @Autowired
    private IServicioClienteRegistrado servicioCliente;

    @Operation(summary = "Crear un nuevo cliente registrado")
    @ApiResponse(responseCode = "200", description = "Cliente registrado creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Error al crear el cliente registrado")
    @PostMapping("/register")
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
    @PutMapping("/actualizarRegistrado")
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
    public ResponseEntity<String> eliminarClienteRegistrado(@PathVariable String email) {
        ResponseEntity<String> response;
        if (servicioCliente.delete(email)) {
            response = ResponseEntity.ok("Cliente registrado eliminado exitosamente");
        } else {
            response = ResponseEntity.badRequest().body("Error al eliminar el cliente registrado");
        }
        return response;
    }

   
}
