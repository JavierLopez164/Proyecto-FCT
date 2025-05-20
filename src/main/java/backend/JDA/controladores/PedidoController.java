package backend.JDA.controladores;

import backend.JDA.modelo.ComidaPK;
import backend.JDA.modelo.Pedido;
import backend.JDA.servicios.ServicioPedidoImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:4200")
public class PedidoController {

    @Autowired
    private ServicioPedidoImpl pedidoService;

    @Operation(summary = "Crear pedido inicial", description = "Crea un pedido si el cliente y restaurante existen.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Cliente o restaurante inválido")
    })
    @PostMapping("/crear-simple")
    public ResponseEntity<?> crearSimple(@RequestParam String email, @RequestParam String restaurante) {
        Optional<Pedido> pedidoOpt = pedidoService.crearPedidoSimple(email, restaurante);
        return pedidoOpt.isPresent()
                ? ResponseEntity.ok(pedidoOpt.get())
                : ResponseEntity.badRequest().body("Cliente o restaurante inválido");
    }

    @Operation(summary = "Añadir comida a pedido", description = "Añade una comida existente a un pedido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comida añadida al pedido"),
            @ApiResponse(responseCode = "400", description = "Pedido o comida no válida")
    })
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

    @Operation(summary = "Cambiar estado de un pedido", description = "Activa o desactiva un pedido por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Pedido no encontrado")
    })
    @PutMapping("/cambiar-estado")
    public ResponseEntity<?> cambiarEstado(@RequestParam String id, @RequestParam boolean activo) {
        Optional<Pedido> pedidoOpt = pedidoService.cambiarEstadoPedido(id, activo);
        return pedidoOpt.isPresent()
                ? ResponseEntity.ok(pedidoOpt.get())
                : ResponseEntity.badRequest().body("Pedido no encontrado");
    }

    @Operation(summary = "Top 5 comidas más pedidas", description = "Devuelve las comidas más pedidas (opcional por restaurante).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de comidas obtenida correctamente")
    })
    @GetMapping("/top5-comidas")
    public ResponseEntity<?> top5Comidas(@RequestParam(required = false) String restaurante) {
        List<Object[]> resultado = restaurante != null
                ? pedidoService.top5ComidasPorRestaurante(restaurante)
                : pedidoService.top5ComidasMasPedidas();

        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Pedidos en los últimos 7 días", description = "Cantidad total de pedidos creados en la última semana.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cantidad de pedidos obtenida correctamente")
    })
    @GetMapping("/ultimos-7-dias")
    public ResponseEntity<?> pedidosUltimos7Dias() {
        int cantidad = pedidoService.pedidosUltimos7Dias();
        return ResponseEntity.ok(cantidad);
    }

    @Operation(summary = "Listar todos los pedidos", description = "Devuelve todos los pedidos registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos listados correctamente")
    })
    @GetMapping("/listar")
    public ResponseEntity<Iterable<Pedido>> listar() {
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }
}

