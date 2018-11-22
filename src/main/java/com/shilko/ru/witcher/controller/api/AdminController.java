package com.shilko.ru.witcher.controller.api;

import com.shilko.ru.witcher.service.AdminService;
import com.shilko.ru.witcher.service.NotificationMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String admin(Model model) {
        model.addAttribute("userList", adminService.getAllUsers());
        return "admin";
    }

    @RequestMapping(value = "/{username}/{action}", method = RequestMethod.GET)
    public String admin(@PathVariable String username, @PathVariable String action, Model model) {
        if (action.equals("disable")) {
            if (!adminService.disableSession(username))
                model.addAttribute("message", "Username is incorrect or error closing session");
            else
                model.addAttribute("message", "Success close session");
        } else if (action.equals("reader")
                || action.equals("editor")
                || action.equals("admin")) {
            Pair<Boolean, Boolean> setRoleSuccess = adminService.setRole(username, action);
            if (!setRoleSuccess.getFirst())
                model.addAttribute("message", "Username is incorrect or error editing role");
            else
                model.addAttribute("message", "Success edit role");
            if (!setRoleSuccess.getSecond())
                model.addAttribute("message2", "Mail didn't send");
            else
                model.addAttribute("message2", "Mail sended");
        }
        model.addAttribute("userList", adminService.getAllUsers());
        return "admin";
    }
}
