package com.shilko.ru.witcher.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shilko.ru.witcher.entity.Users;
import com.shilko.ru.witcher.service.AdminService;
import com.shilko.ru.witcher.service.NotificationMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/getusers", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity admin() {
        List<Users> users = adminService.getAllUsers();
        users.forEach(user -> {
            user.setPassword("");
        });
        return ResponseEntity.ok(new Gson().toJson(users));
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
