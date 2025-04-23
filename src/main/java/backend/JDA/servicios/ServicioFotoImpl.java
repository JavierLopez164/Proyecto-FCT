package backend.JDA.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.JDA.modelo.Foto;
import backend.JDA.repositorios.FotoRepositorio;

@Service
public class ServicioFotoImpl implements IServicioFoto {

	@Autowired
	private FotoRepositorio fotoDAO;

	@Override
	public boolean insert(Foto foto) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(!fotoDAO.existsById(foto.getId())) {
			fotoDAO.save(foto);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean update(Foto foto) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(fotoDAO.existsById(foto.getId())) {
			fotoDAO.save(foto);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		boolean exito = false;
		
		if(fotoDAO.existsById(id)) {
			fotoDAO.deleteById(id);
			exito = true;
		}
		
		return exito;
	}

	@Override
	public List<Foto> findAll() {
		// TODO Auto-generated method stub
		return (List<Foto>) fotoDAO.findAll();
	}

	@Override
	public Optional<Foto> findById(String id) {
		// TODO Auto-generated method stub
		return fotoDAO.findById(id);
	}
	
}
