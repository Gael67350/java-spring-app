package eu.gael67350.api.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.gael67350.api.models.User;

@Repository
public interface IUserRepository extends CrudRepository<User, Integer> {

	Optional<User> findByMail(String mail);
	
	Optional<User> findByToken(String token);
	
}
