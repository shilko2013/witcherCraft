package com.shilko.ru.witcher.controller.api;

import com.shilko.ru.witcher.entity.Users;
import com.shilko.ru.witcher.service.*;
import com.shilko.ru.witcher.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity registration(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                @RequestParam("email") String email) {
        Users user = new Users(username, password, email);

        String errorMessage = userValidator.validate(user);

        if (!errorMessage.equals("success"))
            return ResponseEntity.badRequest().body(errorMessage);

        userService.save(user);

        securityService.autoLogin(user.getUsername(), user.getPassword());

        return ResponseEntity.ok().build();
    }
}
