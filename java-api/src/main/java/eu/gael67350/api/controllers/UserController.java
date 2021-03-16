package eu.gael67350.api.controllers;

import java.util.Optional;

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

import eu.gael67350.api.controllers.dtos.UserDto;
import eu.gael67350.api.errors.exceptions.UserNotFoundException;
import eu.gael67350.api.models.User;
import eu.gael67350.api.services.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{id}")
	@ApiResponse(description = "Données de l'utilisateur correspondant à l'ID fourni", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
	public ResponseEntity<Optional<User>> show(@Parameter(description = "Identifiant unique d'un utilisateur", required = true) @PathVariable int id) {
		return ResponseEntity.ok(userService.show(id));
	}
	
	@GetMapping
	@ApiResponse(description = "Ensemble des utilisateurs correspondant au terme de recherche (si présent)", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class))))
	public ResponseEntity<Iterable<User>> showAll(@Parameter(description = "Le terme utilisé pour la recherche", required = false) @RequestParam(value="q", required=false) String query) {
		if(query != null) {
			return ResponseEntity.ok(userService.search(query));
		}
		
		return ResponseEntity.ok(userService.showAll());
	}
	
	@DeleteMapping("/{id}")
	@ApiResponse(description = "Pas de contenu")
	public ResponseEntity<?> destroy(@Parameter(description = "Identifiant unique d'un utilisateur", required = true) @PathVariable int id) {
		userService.destroy(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PostMapping
	@ApiResponse(description = "Données de l'utilisateur créé", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
	public ResponseEntity<User> store(@Parameter(description = "Objet JSON contenant les données de l'utilisateur à créer", required = true) @RequestBody @Valid User user) {		
		return ResponseEntity.ok(userService.store(user));
	}
	
	@PutMapping("/{id}")
	@ApiResponse(description = "Données de l'utilisateur modifié", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))
	public ResponseEntity<User> update(
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
		
		return ResponseEntity.ok(userService.update(u, id));
	}
}
