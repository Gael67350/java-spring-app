package eu.gael67350.api.services;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import eu.gael67350.api.interfaces.Authenticable;
import eu.gael67350.api.models.User;
import eu.gael67350.api.repositories.IUserRepository;

@Service
public class UserService implements Authenticable {

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

	@Override
	public String login(String username, String password) {
		Optional<User> user = userRepository.findByMail(username);
		
		if(user.isPresent()) {
			User u = user.get();
			
			if(pwdEncoder.matches(password, u.getPassword())) {
				String token = UUID.randomUUID().toString();
				u.setToken(token);
				userRepository.save(u);
				return token;
			}
		}
		
		return StringUtils.EMPTY;
	}
	
	@Override
	public boolean logout(String token) {
		Optional<User> user = userRepository.findByToken(token);
		
		if(user.isPresent()) {
			User u = user.get();
			u.setToken(null);
			userRepository.save(u);
			return true;
		}
		
		return false;
	}

	@Override
	public Optional<org.springframework.security.core.userdetails.User> findByToken(String token) {
		Optional<User> user = userRepository.findByToken(token);
		
		if(user.isPresent()) {
			User u = user.get();
			org.springframework.security.core.userdetails.User securityUser = new org.springframework.security.core.userdetails.User(u.getMail(), u.getPassword(), true, true, true, true, AuthorityUtils.createAuthorityList("ROLE_USER"));
			return Optional.of(securityUser);
		}
	 	
		return Optional.empty();
	}
	
	
	public Optional<User> findByMail(String mail) {
		return userRepository.findByMail(mail);
	}
}
