package backend.JDA.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarFactura(String destinatario, String descripcion, long cantidad, String moneda) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("Factura - App Washabi " + LocalDate.now());
        mensaje.setText("Gracias por tu compra.\n\n"
                + "Descripción: " + descripcion + "\n"
                + "Cantidad: " + (cantidad / 100.0) + " " + moneda.toUpperCase() + "\n\n"
                + "Fecha: " + LocalDate.now() + "\n"
                + "App Washabi");

        mailSender.send(mensaje);
    }
}

