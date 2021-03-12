package eu.gael67350.api.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import eu.gael67350.api.models.User;
import eu.gael67350.api.services.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/auth")
public class TokenController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/token")
	public ResponseEntity<?> generateToken(@RequestParam String mail, @RequestParam String password) {
		String token = userService.login(mail, password);
		
		if(StringUtils.isEmpty(token)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le couple email/mot de passe est invalide.");
		}
		
		Optional<User> user = userService.findByMail(mail);
		return ResponseEntity.ok(user.get());
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> invalidateToken(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		token = StringUtils.removeStart(token, "Bearer").trim();
		
		if(!userService.logout(token)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le token n'est pas valide ou a deja expire.");
		}
		
		return ResponseEntity.ok("");
	}
	
}
