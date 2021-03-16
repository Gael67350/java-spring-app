package eu.gael67350.api.controllers.dtos;

public class UserDto {

	private int id;
	private String firstName;
	private String lastName;
	private String mail;
	private String password;
	
	protected UserDto() {
		
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMail() {
		return mail;
	}

	public String getPassword() {
		return password;
	}
	
}
