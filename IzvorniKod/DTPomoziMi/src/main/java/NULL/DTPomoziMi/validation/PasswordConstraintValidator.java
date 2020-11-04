package NULL.DTPomoziMi.validation;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.StringJoiner;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String>{

    @Override
    public void initialize(ValidPassword constraintAnnotation) {}

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        PasswordValidator val = new PasswordValidator(
                new LengthRule(8,50),
                new CharacterRule(CroatianCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()
        );

        RuleResult result = val.validate(new PasswordData(s));
        if(result.isValid())
            return true;

        StringJoiner sj = new StringJoiner(", ");
        for(String str : val.getMessages(result)) {
            sj.add(str);
        }

        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(sj.toString()).addConstraintViolation();

        return false;
    }
}
