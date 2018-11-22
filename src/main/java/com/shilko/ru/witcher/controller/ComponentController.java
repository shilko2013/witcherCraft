package com.shilko.ru.witcher.controller;

import com.shilko.ru.witcher.entity.Component;
import com.shilko.ru.witcher.entity.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("/component")
public class ComponentController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String component(Model model) {
        return "component";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addComponent(@ModelAttribute("componentForm") Component component, Model model) {

        model.addAttribute("message", "message");
        return "message";
    }
}
