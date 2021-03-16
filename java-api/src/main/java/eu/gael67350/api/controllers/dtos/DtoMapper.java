package eu.gael67350.api.controllers.dtos;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.gael67350.api.errors.exceptions.UserNotFoundException;
import eu.gael67350.api.interfaces.DtoConvertable;
import eu.gael67350.api.models.User;
import eu.gael67350.api.services.UserService;

@Component
public class DtoMapper implements DtoConvertable<User, UserDto> {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto convertToDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public User convertToEntity(UserDto userDto) {
		User user = modelMapper.map(userDto, User.class);
		
		if(userDto.getId() > 0) {
			Optional<User> uOpt = userService.show(userDto.getId());
			
			if(!uOpt.isPresent()) {
				throw new UserNotFoundException();
			}
			
			User u = uOpt.get();
			
			user.setFirstName(u.getFirstName());
			user.setLastName(u.getLastName());
			user.setMail(u.getMail());
			user.setPassword(u.getPassword());
		}
		
		return user;
	}
}
