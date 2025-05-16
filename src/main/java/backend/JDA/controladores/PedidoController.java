package backend.JDA.controladores;

import backend.JDA.modelo.ComidaPK;
import backend.JDA.modelo.Pedido;
import backend.JDA.servicios.ServicioPedidoImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:4200")
public class PedidoController {

    @Autowired
    private ServicioPedidoImpl pedidoService;

    @Operation(summary = "Crear pedido inicial", description = "Crea un pedido solo si el cliente y el restaurante existen.")
    @PostMapping("/crear-simple")
    public ResponseEntity<?> crearSimple(@RequestParam String email, @RequestParam String restaurante) {
        Optional<Pedido> pedidoOpt = pedidoService.crearPedidoSimple(email, restaurante);
        return pedidoOpt.isPresent()
                ? ResponseEntity.ok(pedidoOpt.get())
                : ResponseEntity.badRequest().body("Cliente o restaurante inválido");
    }

    @Operation(summary = "Añadir comida a pedido", description = "Añade una comida a un pedido existente (sin cantidades)")
    @PostMapping("/añadir-comida")
    public ResponseEntity<?> añadirComida(
            @RequestParam String pedidoId,
            @RequestParam String nComida,
            @RequestParam String nRestaurante
    ) {
        ComidaPK comidaPK = new ComidaPK(nComida, nRestaurante);
        Optional<Pedido> pedidoOpt = pedidoService.añadirComida(pedidoId, comidaPK);
        return pedidoOpt.isPresent()
                ? ResponseEntity.ok(pedidoOpt.get())
                : ResponseEntity.badRequest().body("Pedido o comida no válida");
    }

    @Operation(summary = "Listar todos los pedidos")
    @GetMapping("/listar")
    public ResponseEntity<Iterable<Pedido>> listar() {
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

}
