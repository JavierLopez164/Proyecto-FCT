package backend.JDA.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import backend.JDA.servicios.IServicioFoto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
/*
@RestController
@RequestMapping("api/fotos")
@CrossOrigin(origins = "http://localhost:4200")
public class FotoController {
	  
	  @Autowired
	  private IServicioFoto servicioFoto;
	  @PostMapping("/subir")
	  @Operation(summary = "Subida al cloud de una imagen seleccionado de su galeria")
		@ApiResponse(responseCode = "200", description = "Se ha subido exitosamente a la nube de Cloudinary")
		@ApiResponse(responseCode = "400", description = "Error no se ha subido la imagen a Cloudinary")
	    public ResponseEntity<String> subirFoto(@RequestParam MultipartFile imagenFichero,@RequestParam String email) {
		  	String mensaje="No ha habido exito al subir la foto";
			HttpStatus status = HttpStatus.BAD_REQUEST;
	        try {
	          
	        	if(servicioFoto.subirImagenACloud(imagenFichero, email)) {
	        		mensaje="Exito al subir la foto";
	        		status=HttpStatus.OK;
	        	}
	        

	        } catch (Exception e) {
	        	status=HttpStatus.INTERNAL_SERVER_ERROR;
	        }
	        
	        return new ResponseEntity<>(mensaje,status);
	        
	    }
}
*/