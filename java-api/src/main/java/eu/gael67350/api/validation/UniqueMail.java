package eu.gael67350.api.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Constraint(validatedBy = UniqueMailValidator.class)
public @interface UniqueMail {
	String message() default "{org.hibernate.validator.unique.mail.message}";
	
	Class<?>[] groups() default {};
	
	Class <? extends Payload>[] payload() default {};
	
	@Retention(RUNTIME)
	@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
	@Documented
	public @interface List {
		UniqueMail[] value();
	}
}
