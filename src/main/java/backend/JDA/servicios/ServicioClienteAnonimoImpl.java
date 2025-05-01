package backend.JDA.servicios;

import backend.JDA.modelo.ClienteAnonimo;
import backend.JDA.repositorios.ClienteRepositorioAnonimo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ServicioClienteAnonimoImpl implements IServicioClienteAnonimo {

    @Autowired
    private ClienteRepositorioAnonimo clienteRepositorio;

    @Override
    public boolean insert(ClienteAnonimo cliente) {
        LocalDateTime ahora = LocalDateTime.now();
        cliente.setFechaCreado(ahora);
        cliente.setFechaExpiracion(ahora.plusHours(12));
        clienteRepositorio.save(cliente);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        if (clienteRepositorio.existsById(id)) {
            clienteRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<ClienteAnonimo> findById(Long id) {
        return clienteRepositorio.findById(id);
    }

    @Scheduled(fixedRate = 60000) // 1 Hora = 3600000
    public void eliminarClientesExpirados() {
        clienteRepositorio.deleteByFechaExpiracionBefore(LocalDateTime.now());
    }
}
