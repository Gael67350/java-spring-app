package eu.gael67350.api.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import eu.gael67350.api.repositories.IUserRepository;

public class UniqueMailValidator implements ConstraintValidator<UniqueMail, String> {
	
	@Autowired
	private IUserRepository userRepository;
	

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null) {
			return false;
		}
		
		// TODO : retourne NullPointerException lorsque le test est OK
		if(!userRepository.findByMail(value).isEmpty()) {
			return false;
		}
	
		return true;
	}

}
