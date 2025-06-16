package backend.JDA.servicios;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.JDA.modelo.Cliente;
import backend.JDA.modelo.VerificacionToken;
import backend.JDA.repositorios.VerificacionTokenRepositorio;

@Service
public class VerificacionService {

    @Autowired
    private VerificacionTokenRepositorio tokenRepo;

    @Autowired
    private EmailService emailService;

    public void crearYEnviarToken(Cliente cliente) {
        String token = UUID.randomUUID().toString();

        VerificacionToken verificacion = new VerificacionToken();
        verificacion.setToken(token);
        verificacion.setCliente(cliente);
        verificacion.setFechaExpiracion(LocalDateTime.now().plusDays(1));

        tokenRepo.save(verificacion);

        String enlace = "http://localhost:8080/api/verificacion/verificar?token=" + token;

        String cuerpo = "Hola " + cliente.getNombre() + ",\n\n"
                + "Gracias por registrarte. Haz clic en el siguiente enlace para activar tu cuenta:\n\n"
                + "<a href=\"" + enlace + "\">Activar cuenta</a>";

        emailService.enviarCorreoHtml(cliente.getEmail(), "Verifica tu cuenta", cuerpo);
    }
}
