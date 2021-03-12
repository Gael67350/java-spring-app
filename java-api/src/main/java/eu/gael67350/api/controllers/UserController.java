package eu.gael67350.api.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import eu.gael67350.api.models.User;
import eu.gael67350.api.services.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{id}")
	public Optional<User> show(@PathVariable int id) {
		return userService.show(id);
	}
	
	@GetMapping
	public Iterable<User> showAll(@RequestParam(value="q", required=false) String query) {
		if(query != null) {
			return userService.search(query);
		}
		
		return userService.showAll();
	}
	
	@DeleteMapping("/{id}")
	public void destroy(@PathVariable int id) {
		userService.destroy(id);
	}
	
	@PostMapping
	public User store(@RequestBody @Valid User user) {		
		return userService.store(user);
	}
	
	@PutMapping("/{id}")
	public User update(@RequestBody @Valid User updatedUser, @PathVariable int id) {
		return userService.update(updatedUser, id);
	}
}
