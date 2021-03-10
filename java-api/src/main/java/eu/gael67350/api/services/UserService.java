package eu.gael67350.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import eu.gael67350.api.models.User;
import eu.gael67350.api.repositories.IUserRepository;

@Service
public class UserService {

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	public Optional<User> show(int id) {
		return userRepository.findById(id);
	}
	
	public Iterable<User> showAll() {
		return userRepository.findAll();
	}
	
	public void destroy(int id) {
		userRepository.deleteById(id);
	}
	
	public User store(User user) {
		user.setPassword(pwdEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public User update(User user, int id) {
		return userRepository.findById(id)
		.map(e -> {
			e.setFirstName(user.getFirstName());
			e.setLastName(user.getLastName());
			e.setMail(user.getMail());
			e.setPassword(pwdEncoder.encode(user.getPassword()));
			return userRepository.save(e);
		})
		.orElseGet(() -> {
			user.setId(id);
			return userRepository.save(user);
		});
	}
}
