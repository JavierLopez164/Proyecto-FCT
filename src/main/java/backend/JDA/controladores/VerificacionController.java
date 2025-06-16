package backend.JDA.controladores;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.JDA.modelo.Cliente;
import backend.JDA.modelo.VerificacionToken;
import backend.JDA.repositorios.ClienteRepositorio;
import backend.JDA.repositorios.VerificacionTokenRepositorio;

@RestController
@RequestMapping("api/verificacion")
@CrossOrigin(origins = "http://localhost:4200")
public class VerificacionController {
	
	@Autowired
	private VerificacionTokenRepositorio tokenRepo;
	
    @Autowired
    private ClienteRepositorio clienteRepo;
	
    @GetMapping("/verificar")
    public ResponseEntity<String> verificarCuenta(@RequestParam String token) {
        Optional<VerificacionToken> optionalToken = tokenRepo.findByToken(token);

        if (optionalToken.isPresent()) {
            VerificacionToken verificacion = optionalToken.get();
            Cliente cliente = verificacion.getCliente();

            if (verificacion.getFechaExpiracion().isBefore(LocalDateTime.now())) {
                return ResponseEntity.badRequest().body("Token expirado.");
            }

            cliente.setActivo(true);
            clienteRepo.save(cliente);
            tokenRepo.delete(verificacion); // opcional: limpiar token

            return ResponseEntity.ok("Cuenta activada correctamente.");
        } else {
            return ResponseEntity.badRequest().body("Token inválido.");
        }
    }
	
}
