package edu.jong.board.role.type;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.springframework.util.AntPathMatcher;

@Constraint(validatedBy = { })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface AntPahtPattern {

	String message() default "{javax.validation.constraints.NotBlank.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	public static class Validator implements ConstraintValidator<AntPahtPattern, String> {

		private static final AntPathMatcher MATCHER = new AntPathMatcher();
		
		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			return (value == null) ? null : MATCHER.isPattern(value);
		}
	}
}
