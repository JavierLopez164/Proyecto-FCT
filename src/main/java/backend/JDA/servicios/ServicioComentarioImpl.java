package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.JDA.modelo.Comentario;
import backend.JDA.repositorios.ComentarioRepositorio;

@Service
public class ServicioComentarioImpl implements IServicioComentario {

	@Autowired
	private ComentarioRepositorio comentarioDAO;

	@Override
	public boolean insert(Comentario comentario) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(!comentarioDAO.existsById(comentario.getId())) {
			comentarioDAO.save(comentario);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean update(Comentario comentario) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(comentarioDAO.existsById(comentario.getId())) {
			comentarioDAO.save(comentario);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(comentarioDAO.existsById(id)) {
			comentarioDAO.deleteById(id);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public List<Comentario> findAll() {
		// TODO Auto-generated method stub
		return (List<Comentario>) comentarioDAO.findAll();
	}

	@Override
	public Optional<Comentario> findById(String id) {
		// TODO Auto-generated method stub
		return comentarioDAO.findById(id);
	}
	
}
