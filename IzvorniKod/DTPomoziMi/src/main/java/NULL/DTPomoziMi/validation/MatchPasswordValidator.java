package NULL.DTPomoziMi.validation;

import NULL.DTPomoziMi.web.DTO.UserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchPasswordValidator implements ConstraintValidator<MatchPassword, Object> {


    @Override
    public void initialize(MatchPassword constraintAnnotation) {}

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        UserDTO user = (UserDTO) obj;

        return user.getPassword().equals(user.getSecondPassword());
    }
}
