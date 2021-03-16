package eu.gael67350.api.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.gael67350.api.controllers.dtos.DtoMapper;
import eu.gael67350.api.controllers.dtos.UserDto;
import eu.gael67350.api.errors.exceptions.UserNotFoundException;
import eu.gael67350.api.models.User;
import eu.gael67350.api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Utilisateurs")
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DtoMapper dtoMapper;
	
	@GetMapping("/{id}")
	@ApiResponse(description = "Données de l'utilisateur correspondant à l'ID fourni", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<UserDto> show(@Parameter(description = "Identifiant unique d'un utilisateur", required = true) @PathVariable int id) {
		Optional<User> uOpt = userService.show(id);
		
		if(!uOpt.isPresent()) {
			throw new UserNotFoundException();
		}
		
		return ResponseEntity.ok(dtoMapper.convertToDto(uOpt.get()));
	}
	
	@GetMapping
	@ApiResponse(description = "Ensemble des utilisateurs correspondant au terme de recherche (si présent)", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDto.class))))
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<List<UserDto>> showAll(@Parameter(description = "Le terme utilisé pour la recherche", required = false) @RequestParam(value="q", required=false) String query) {
		List<User> users;
		
		if(query != null) {
			 users = userService.search(query);
		}else {
			users = userService.showAll();
		}
		
		List<UserDto> dtos = users.stream().map(dtoMapper::convertToDto).collect(Collectors.toList());
		return ResponseEntity.ok(dtos);
	}
	
	@DeleteMapping("/{id}")
	@ApiResponse(description = "Pas de contenu", responseCode = "204")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<?> destroy(@Parameter(description = "Identifiant unique d'un utilisateur", required = true) @PathVariable int id) {
		userService.destroy(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PostMapping
	@ApiResponse(description = "Données de l'utilisateur créé", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<UserDto> store(@Parameter(description = "Objet JSON contenant les données de l'utilisateur à créer", required = true) @RequestBody @Valid UserDto userDto) {
		User user = dtoMapper.convertToEntity(userDto);
		UserDto dto = dtoMapper.convertToDto(userService.store(user));
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}
	
	@PutMapping("/{id}")
	@ApiResponse(description = "Données de l'utilisateur modifié", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<UserDto> update(
			@Parameter(description = "Objet JSON contenant les données modifiées de l'utilisateur", required = true) @RequestBody @Valid UserDto userDto, 
			@Parameter(description = "Identifiant unique d'un utilisateur") @PathVariable int id
			) {
		Optional<User> user = userService.show(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException();
		}
		
		User u = SerializationUtils.clone(user.get());
		
		if(userDto.getFirstName() != null) {
			u.setFirstName(userDto.getFirstName());
		}
		
		if(userDto.getLastName() != null) {
			u.setLastName(userDto.getLastName());
		}
		
		if(userDto.getMail() != null) {
			u.setMail(userDto.getMail());
		}
		
		if(userDto.getPassword() != null) {
			u.setPassword(userDto.getPassword());
		}
		
		UserDto dto = dtoMapper.convertToDto(userService.update(u, id));
		return ResponseEntity.ok(dto);
	}
}
