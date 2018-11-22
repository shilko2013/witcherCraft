package com.shilko.ru.witcher.controller.api;

import com.google.gson.Gson;
import com.shilko.ru.witcher.entity.Component;
import com.shilko.ru.witcher.entity.Image;
import com.shilko.ru.witcher.entity.Users;
import com.shilko.ru.witcher.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/component")
public class ComponentController {

    @Autowired
    private ComponentService componentService;

    @RequestMapping(value = "/components", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity getAllComponents() {
        List<Component> components = componentService.getAllComponents();
        components.forEach(component -> {
            component.setImage(null);
        });
        return ResponseEntity.ok(new Gson().toJson(componentService.getAllComponents()));
    }

    @RequestMapping(value = "/{id}/image", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity getImageByIdComponent(@PathVariable String strId) {
        long id;
        try {
            id = Long.parseLong(strId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal id");
        }
        Optional<Image> image = componentService.getImageByIdComponent(id);
        Image returnImage;
        if (!image.isPresent())
            returnImage = new Image()
        return ResponseEntity.ok(new Gson().toJson(componentService.getAllComponents()));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addComponent(@ModelAttribute("componentForm") Component component, Model model) {

        model.addAttribute("message", "message");
        return "message";
    }
}
