package com.shilko.ru.witcher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration() {
        return "registration";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome() {
        return "welcome";
    }

    @RequestMapping(value = "/api/component/addcategory", method = RequestMethod.GET)
    public String addCategoryComponentPage() {
        return "addcategory";
    }

    @RequestMapping(value = "/api/thing/addtype", method = RequestMethod.GET)
    public String addTypeThingPage() {
        return "addtype";
    }

    @RequestMapping(value = "/api/component/add", method = RequestMethod.GET)
    public String addComponentPage() {
        return "addcomponent";
    }

    @RequestMapping(value = "/api/thing/add", method = RequestMethod.GET)
    public String addThingPage() {
        return "addthing";
    }
}
