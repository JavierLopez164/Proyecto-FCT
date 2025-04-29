package backend.JDA.controladores;

import backend.JDA.servicios.IServicioClienteAnonimo;
import backend.JDA.modelo.ClienteAnonimo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/clientes-anonimos")
public class ClienteAnonimoController {

    @Autowired
    private IServicioClienteAnonimo servicioCliente;

    @Operation(summary = "Crear un nuevo cliente anónimo")
    @ApiResponse(responseCode = "200", description = "Cliente anónimo creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Error al crear el cliente anónimo")
    @PostMapping("/")
    public ResponseEntity<String> crearClienteAnonimo(@RequestBody ClienteAnonimo clienteAnonimo) {
        ResponseEntity<String> response;
        if (servicioCliente.insert(clienteAnonimo)) {
            response = ResponseEntity.ok("Cliente anónimo creado exitosamente");
        } else {
            response = ResponseEntity.badRequest().body("Error al crear el cliente anónimo");
        }
        return response;
    }

    @Operation(summary = "Obtener cliente anónimo por ID")
    @ApiResponse(responseCode = "200", description = "Cliente anónimo encontrado")
    @ApiResponse(responseCode = "404", description = "Cliente anónimo no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerClienteAnonimo(@PathVariable Long id) {
        ResponseEntity<?> response;
        Optional<ClienteAnonimo> cliente = servicioCliente.findById(id)
                .filter(c -> c instanceof ClienteAnonimo)
                .map(c -> (ClienteAnonimo) c);
        if (cliente.isPresent()) {
            response = ResponseEntity.ok(cliente.get());
        } else {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @Operation(summary = "Eliminar cliente anónimo por ID")
    @ApiResponse(responseCode = "200", description = "Cliente anónimo eliminado exitosamente")
    @ApiResponse(responseCode = "400", description = "Error al eliminar el cliente anónimo")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarClienteAnonimo(@PathVariable Long id) {
        ResponseEntity<String> response;
        if (servicioCliente.delete(id)) {
            response = ResponseEntity.ok("Cliente anónimo eliminado exitosamente");
        } else {
            response = ResponseEntity.badRequest().body("Error al eliminar el cliente anónimo");
        }
        return response;
    }
}
