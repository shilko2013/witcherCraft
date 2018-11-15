package com.shilko.ru.witcher.controller;

import com.shilko.ru.witcher.entity.Users;
import com.shilko.ru.witcher.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        model.addAttribute("userList", adminService.getAllUsers());
        return "admin";
    }

    @RequestMapping(value = "/admin/{username}/{action}", method = RequestMethod.GET)
    public String admin(@PathVariable String username, @PathVariable String action, Model model) {
        model.addAttribute("userList", adminService.getAllUsers());
        if (action.equals("disable")) {
            if (!adminService.disableSession(username))
                model.addAttribute("message", "Username is incorrect or error closing session");
            else
                model.addAttribute("message", "Success close session");
        } else if (action.equals("reader")
                || action.equals("editor")
                || action.equals("admin")) {
            if (!adminService.setRole(username, action))
                model.addAttribute("message", "Username is incorrect or error editing role");
            else
                model.addAttribute("message", "Success edit role");
        }
        return "admin";
    }
}
