package backend.JDA.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@EntityListeners(PasswordListener.class)
public class Cliente {
	//@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@EqualsAndHashCode.Include
	@Id
	@Email(message = "El correo debe ser válido")
	@NotBlank(message = "El correo no puede estar vacío")
	@Column(name = "pk_email", length = 40, nullable = false)
	private String email;
	@Column(name = "nombre", length = 20, nullable = false)
	private String nombre;
	@Column(name = "contrasenia", nullable = false)
	private String contrasenia;
	@Builder.Default
	@Enumerated(EnumType.STRING)
	private Rol rol=Rol.ROLE_USER;
	@Builder.Default
	private LocalDate fechaCreacion=LocalDate.now();
	@Builder.Default
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "fotoId")
	private Foto fotoPerfil=Foto.builder().fecha(LocalDate.now()).url("/img/imagen_usuario_por_defecto.jpg").build();
	 @Column(name = "restaurante", length = 50, nullable = true)
	 private String restaurante;

	
	
}
