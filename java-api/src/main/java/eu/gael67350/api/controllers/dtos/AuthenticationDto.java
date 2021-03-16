package eu.gael67350.api.controllers.dtos;

public class AuthenticationDto {

	private UserDto user;
	
	private String token;
	
	public AuthenticationDto() {
		
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
