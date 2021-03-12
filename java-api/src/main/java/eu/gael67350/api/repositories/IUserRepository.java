package eu.gael67350.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import eu.gael67350.api.models.User;

@Repository
public interface IUserRepository extends CrudRepository<User, Integer> {

	Optional<User> findByMail(String mail);
	
	Optional<User> findByToken(String token);
	
	@Query("SELECT u FROM User u WHERE first_name LIKE %:query% OR last_name LIKE %:query% OR mail LIKE %:query%")
	Iterable<User> search(@Param("query") String query);
}
