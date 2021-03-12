package eu.gael67350.api.interfaces;

import java.util.Optional;

public interface Authenticable {

	/**
	 * User authentication method that provides an API token if the user name/password pair is valid.
	 * 
	 * @param username	User's provided user name
	 * @param password	User's provided password
	 * @return 			A valid API token if authentication is successful, an empty string otherwise
	 */
	public String login(String username, String password);
	
	/**
	 * Invalidate the API token passed in parameter.
	 * 
	 * @param token	A valid API token
	 * @return		true if the token was valid and invalidated successfully, false otherwise
	 */
	public boolean logout(String token);
	
	/**
	 * Returns the user who owns the specified token.
	 * 
	 * @param token	API token
	 * @return		The user who owns the token if it exists, otherwise null
	 */
	public Optional<org.springframework.security.core.userdetails.User> findByToken(String token);
	
}
