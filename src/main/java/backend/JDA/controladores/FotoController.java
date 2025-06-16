package backend.JDA.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import backend.JDA.modelo.ComidaPK;
import backend.JDA.modelo.Foto;
import backend.JDA.modelo.dto.ClienteFotoDto;
import backend.JDA.modelo.dto.ComidaFotoDto;
import backend.JDA.servicios.IServicioComida;
import backend.JDA.servicios.IServicioFoto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/fotos")
@CrossOrigin(origins = "http://localhost:4200")
public class FotoController {
	  
	  @Autowired
	  private IServicioFoto servicioFoto;

	@PostMapping(value = "/actualizarfotoperfil", consumes = "multipart/form-data")
	@Operation(summary = "Actualiza la foto de perfil del cliente autenticado")
	@ApiResponse(responseCode = "200", description = "Foto de perfil actualizada correctamente")
	@ApiResponse(responseCode = "404", description = "Cliente no encontrado")
	public ResponseEntity<ClienteFotoDto> actualizarFotoPerfil(@RequestPart MultipartFile imagenFichero, @RequestParam String email) {

		// Este método ya debe existir, lo reutilizas para actualizar la imagen
		return ResponseEntity.of(servicioFoto.subirImagenACloudFotoPerfil(imagenFichero, email));
	}



	@PostMapping(value="/subirfotocomida",consumes = "multipart/form-data")
	  @Operation(summary = "Subida al cloud de una imagen de comida tomado desde la cámara")
		@ApiResponse(responseCode = "200", description = "Se ha subido exitosamente a la nube de Cloudinary")
		@ApiResponse(responseCode = "400", description = "Error no se ha subido la imagen a Cloudinary")
	    public ResponseEntity<ComidaFotoDto> subirFotoComida(@RequestPart MultipartFile imagenFichero , @RequestParam String comida,@RequestParam String restaurante) {


		  return ResponseEntity.of(servicioFoto.subirImagenACloudComida(imagenFichero, new ComidaPK(comida,restaurante)));
	    }
		
}
