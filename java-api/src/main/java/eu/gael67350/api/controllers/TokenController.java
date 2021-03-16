package eu.gael67350.api.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import eu.gael67350.api.controllers.dtos.AuthenticationDto;
import eu.gael67350.api.controllers.dtos.DtoMapper;
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
@Tag(name = "Authentification")
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/auth")
public class TokenController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private DtoMapper dtoMapper;
	
	@PostMapping("/token")
	@ApiResponse(description = "Données de l'utilisateur connecté avec token d'identification API valide", responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AuthenticationDto.class))))
	public ResponseEntity<AuthenticationDto> generateToken(
			@Parameter(description = "Adresse email", required = true) @RequestParam String mail, 
			@Parameter(description = "Mot de passe", required = true) @RequestParam String password
			) {
		String token = userService.login(mail, password);
		
		if(StringUtils.isEmpty(token)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le couple email/mot de passe est invalide.");
		}
		
		Optional<User> user = userService.findByMail(mail);
		
		AuthenticationDto authDto = new AuthenticationDto();
		authDto.setUser(dtoMapper.convertToDto(user.get()));
		authDto.setToken(token);
		
		return ResponseEntity.ok(authDto);
	}
	
	@PostMapping("/logout")
	@ApiResponse(description = "Pas de contenu", responseCode = "204")
	@Operation(security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<?> invalidateToken(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		token = StringUtils.removeStart(token, "Bearer").trim();
		
		if(!userService.logout(token)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le token n'est pas valide ou a deja expire.");
		}
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
