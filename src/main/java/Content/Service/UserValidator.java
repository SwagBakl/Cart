package Content.Service;

import Content.entity.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == User.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "user.name.empty");
        ValidationUtils.rejectIfEmpty(errors, "password", "user.password.empty");

        User user = (User) target;
        if (user.getUsername() != null && user.getUsername().length() < 3 ||
                user.getUsername().length() > 20) {
            errors.rejectValue("name", "user.name.size");
        }

        if (user.getPassword() != null && user.getPassword().contains(" ")) {
            errors.rejectValue("password", "user.password.space");
        }

        if (user.getPassword() != null && user.getPassword().length() < 5 &&
                user.getPassword().length() > 15) {
            errors.rejectValue("password", "user.password.size");
        }
    }
}
