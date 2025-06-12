package backend.JDA.controladores;

import backend.JDA.modelo.ComidaPK;
import backend.JDA.modelo.Pedido;
import backend.JDA.modelo.dto.PedidoListadoDTO;
import backend.JDA.modelo.dto.TopComidaDTO;
import backend.JDA.servicios.IServicioPedido;
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
@RequestMapping("api/pedidos")
@CrossOrigin(origins = "http://localhost:4200")
public class PedidoController {

    @Autowired
    private IServicioPedido pedidoService;

    @Operation(summary = "Crear pedido inicial", description = "Crea un pedido si el cliente y restaurante existen.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Cliente o restaurante inválido")
    })
    @PostMapping("/crear-simple")
    public ResponseEntity<?> crearSimple(@RequestParam String email, @RequestParam String restaurante) {
        return ResponseEntity.ok(pedidoService.crearPedidoSimple(email, restaurante));
               
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

    @Operation(summary = "Restar una unidad de comida en un pedido", description = "Disminuye en 1 la cantidad de una comida en el pedido. Si queda en 0, se elimina.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comida actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Pedido o comida no válida")
    })
    @PostMapping("/restar-comida")
    public ResponseEntity<?> restarComida(
            @RequestParam String pedidoId,
            @RequestParam String nComida,
            @RequestParam String nRestaurante
    ) {
        ComidaPK comidaPK = new ComidaPK(nComida, nRestaurante);
        Optional<Pedido> pedidoOpt = pedidoService.restarComida(pedidoId, comidaPK);
        return pedidoOpt.isPresent()
                ? ResponseEntity.ok(pedidoOpt.get())
                : ResponseEntity.badRequest().body("Pedido o comida no válida");
    }

    @Operation(summary = "Eliminar completamente una comida del pedido", description = "Quita completamente un ítem del pedido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "Pedido o comida no válida")
    })
    @DeleteMapping("/eliminar-comida")
    public ResponseEntity<?> eliminarComida(
            @RequestParam String pedidoId,
            @RequestParam String nComida,
            @RequestParam String nRestaurante
    ) {
        ComidaPK comidaPK = new ComidaPK(nComida, nRestaurante);
        Optional<Pedido> pedidoOpt = pedidoService.eliminarComida(pedidoId, comidaPK);
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
        List<TopComidaDTO> resultado = pedidoService.top5ComidasPorRestaurante(restaurante);

        return ResponseEntity.ok(resultado);
    }

    @Operation(summary = "Top 5 comidas más pedidas por restaurante", description = "Devuelve el Top 5 de comidas para cada restaurante.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mapa de restaurantes con sus Top 5 comidas")
    })
    @GetMapping("/top5-comidas/todos")
    public ResponseEntity<?> top5ComidasTodosRestaurantes() {
        List<TopComidaDTO> resultado = pedidoService.top5ComidasMasPedidas();
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
    public ResponseEntity<?> listar() {
        List<PedidoListadoDTO> dtos = pedidoService.listarPedidosDTO(); // nuevo método
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Últimos 5 pedidos de un usuario", description = "Devuelve los últimos 5 pedidos realizados por el usuario indicado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos obtenidos correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no tiene pedidos")
    })
    @GetMapping("/ultimos5")
    public ResponseEntity<?> ultimos5Pedidos(@RequestParam String email) {
        List<PedidoListadoDTO> pedidos = pedidoService.ultimos5PedidosDeUsuario(email);
        return pedidos.isEmpty()
                ? ResponseEntity.status(404).body("No hay pedidos para este usuario")
                : ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Añadir comida a pedido", description = "Añade todas las comidas existente a un pedido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comidas añadida al pedido"),
            @ApiResponse(responseCode = "400", description = "Pedido o comidas no válida")
    })
    @PostMapping("/aniadircomidas")
    public ResponseEntity<?> aniadirComida(
            @RequestParam String pedidoId,
            @RequestParam String nComida,
            @RequestParam String nRestaurante,
            @RequestParam  int cantidad,
            @RequestParam  int total
    ) {
        ComidaPK comidaPK = new ComidaPK(nComida, nRestaurante);
        Optional<Pedido> pedidoOpt = pedidoService.aniadirComidas(pedidoId, comidaPK,cantidad,total);
        return pedidoOpt.isPresent()
                ? ResponseEntity.ok(pedidoOpt.get())
                : ResponseEntity.badRequest().body("Pedido o comida no válida");
    }

    @Operation(summary = "Eliminar un pedido", description = "Elimina un pedido completamente de la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarPedido(@RequestParam String id) {
        boolean eliminado = pedidoService.eliminarPedido(id);
        if (eliminado) {
            return ResponseEntity.ok("Pedido eliminado correctamente");
        } else {
            return ResponseEntity.status(404).body("Pedido no encontrado");
        }
    }

}

