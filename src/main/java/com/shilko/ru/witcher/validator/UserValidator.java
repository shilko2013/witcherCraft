package com.shilko.ru.witcher.validator;

import com.shilko.ru.witcher.entity.Users;
import com.shilko.ru.witcher.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Users.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Users user = (Users) o;

        if (user.getUsername() == null)
            errors.rejectValue("username", "Invalid.userForm.username");
        else {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
            if (user.getUsername().length() < 8 || user.getUsername().length() > 32) {
                errors.rejectValue("username", "Size.userForm.username");
            }

            if (userService.findByUsername(user.getUsername()) != null) {
                errors.rejectValue("username", "Duplicate.userForm.username");
            }

            if (!checkValidChars(user.getUsername()))
                errors.rejectValue("username", "Invalid.userForm.username");
        }

        if (user.getPassword() == null) {
            errors.rejectValue("password", "Invalid.userForm.password");
        } else {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
            if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
                errors.rejectValue("password", "Size.userForm.password");
            }

            if (!checkValidChars(user.getPassword()))
                errors.rejectValue("password", "Invalid.userForm.password");
        }

        if (user.getEmail() == null) {
            errors.rejectValue("email", "Invalid.userForm.email");
        }
        else {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Required");
            if (!user.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                errors.rejectValue("email", "Invalid.userForm.email");
            }

            if (userService.findByEmail(user.getEmail()) != null) {
                errors.rejectValue("email", "Duplicate.userForm.email");
            }
        }

    }

    private boolean checkValidChars(String login) {
        return login.chars().allMatch(c ->
                (c >= '0' && c <= '9') ||
                        (c >= 'a' && c <= 'z') ||
                        (c >= 'A' && c <= 'Z') ||
                        c == '_'
        );
    }

}
