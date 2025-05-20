package backend.JDA.controladores;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import backend.JDA.modelo.ComidaPK;
import backend.JDA.modelo.dto.ClienteFotoDto;
import backend.JDA.modelo.dto.ComidaFotoDto;
import backend.JDA.servicios.IServicioComida;
import backend.JDA.servicios.IServicioFoto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("api/fotos")
@CrossOrigin(origins = "http://localhost:4200")
public class FotoController {
	  
	  @Autowired
	  private IServicioFoto servicioFoto;
	
	  @PostMapping(value="/subirfotoperfil",consumes = "multipart/form-data")
	  @Operation(summary = "Subida al cloud de una imagen de perfil seleccionado de su galeria")
		@ApiResponse(responseCode = "200", description = "Se ha subido exitosamente a la nube de Cloudinary")
		@ApiResponse(responseCode = "400", description = "Error no se ha subido la imagen a Cloudinary")
	    public ResponseEntity<ClienteFotoDto> subirFoto(@RequestPart MultipartFile imagenFichero , @RequestParam String email) {
	        return ResponseEntity.of(servicioFoto.subirImagenACloudFotoPerfil(imagenFichero, email));
	    }
	  
	  
	  @PostMapping(value="/subirfotocomida",consumes = "multipart/form-data")
	  @Operation(summary = "Subida al cloud de una imagen de comida tomado desde la cámara")
		@ApiResponse(responseCode = "200", description = "Se ha subido exitosamente a la nube de Cloudinary")
		@ApiResponse(responseCode = "400", description = "Error no se ha subido la imagen a Cloudinary")
	    public ResponseEntity<ComidaFotoDto> subirFotoComida(@RequestPart MultipartFile imagenFichero , @RequestParam String comida,@RequestParam String restaurante,@RequestParam String email) {
	        return ResponseEntity.of(servicioFoto.subirImagenACloudComida(imagenFichero, new ComidaPK(comida,restaurante),email));
	    }
}
