package com.shilko.ru.witcher.validator;

import com.shilko.ru.witcher.entity.Users;
import com.shilko.ru.witcher.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserValidator {

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public String validate(Object o) {
        Users user = (Users) o;

        if (user.getUsername() == null)
            return "Invalid username";
        else {
            if (user.getUsername().length() < 8 || user.getUsername().length() > 32) {
                return "Size username";
            }

            if (userService.findByUsername(user.getUsername()) != null) {
                return "Duplicate username";
            }

            if (!checkValidChars(user.getUsername()))
                return "Invalid username";
        }

        if (user.getPassword() == null) {
            return  "Invalid password";
        } else {
            if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
                return "Size password";
            }

            if (!checkValidChars(user.getPassword()))
                return "Invalid password";
        }

        if (user.getEmail() == null) {
            return "Invalid email";
        }
        else {
            if (!user.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                return "Invalid email";
            }

            if (userService.findByEmail(user.getEmail()) != null) {
                return "Duplicate email";
            }
        }
        return "success";
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
