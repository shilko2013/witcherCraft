package com.shilko.ru.witcher.controller.api;

import com.google.gson.Gson;
import com.shilko.ru.witcher.entity.*;
import com.shilko.ru.witcher.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/component")
public class ComponentController {

    @Autowired
    private ComponentService componentService;

    @Value("classpath:standard_picture.png")
    Resource standardImage;

    private final static long MAX_FILE_SIZE = 0xA00000; //10Mb

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

    @RequestMapping(value = "/{strId}/image", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity getImageByIdComponent(@PathVariable String strId) {
        long id;
        final HttpHeaders headers = new HttpHeaders();
        try {
            id = Long.parseLong(strId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal id");
        }
        Optional<Image> image = componentService.getImageByIdComponent(id);
        if (!image.isPresent()) {
            headers.setContentType(MediaType.IMAGE_PNG);
            try {
                return new ResponseEntity<>(StreamUtils.copyToByteArray(standardImage.getInputStream()), headers, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        headers.setContentType(Image.getMediaType(image.get()));
        return new ResponseEntity<>(image.get().getPicture(), headers, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity addComponent(@RequestParam("name") String name,
                                @RequestParam("price") int price,
                                @RequestParam("weight") double weight,
                                @RequestParam("description") String description,
                                @RequestParam("categoryId") long categoryId,
                                @RequestParam("isAlchemy") boolean isAlchemy,
                                @RequestParam("image") MultipartFile imageFile) {
        DescriptionComponent descriptionComponent = new DescriptionComponent(description);
        Optional<CategoryComponent> categoryComponent = componentService.getCategoryComponentById(categoryId);
        if (!categoryComponent.isPresent())
            return ResponseEntity.badRequest().body("Illegal category id");
        Image image = new Image();
        String[] split;
        if (imageFile == null)
            image = null;
        else {
            if (imageFile.getSize() > MAX_FILE_SIZE)
                return ResponseEntity.badRequest().body("So big image file, maximum size is " + (MAX_FILE_SIZE >> 20) + "Mb");
            try {
                split = imageFile.getOriginalFilename().split("\\.");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Illegal image file");
            }
            String imageType = split[split.length - 1];
            image.setType(imageType);
            if (Image.getMediaType(image) == MediaType.ALL)
                return ResponseEntity.badRequest().body("Illegal image file extension. Server supports png, jpeg, jpg and gif images.");
            try {
                image.setPicture(imageFile.getBytes());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Illegal image file");
            }
        }
        Component component = new Component(name, price, weight, descriptionComponent, categoryComponent.get(), isAlchemy, image);
        descriptionComponent.setComponent(component);
        if (image != null)
            image.setComponent(component);
        try {
            componentService.saveComponent(component, image, descriptionComponent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal set of arguments");
        }
        return ResponseEntity.ok("Successfully added");
    }

    @Transactional
    @RequestMapping(value = "/addcategory", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity addCategoryComponent(@RequestParam("name") String name,
                                        @RequestParam("information") String information) {
        if (name == null || information == null)
            return ResponseEntity.badRequest().body("Illegal set of arguments");
        try {
            if (componentService.getCategoryComponentByName(name).isPresent())
                return ResponseEntity.badRequest().body("This category already exists");
            componentService.saveCategoryComponent(new CategoryComponent(name, information));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal set of arguments");
        }
        return ResponseEntity.ok("Successfully added");
    }
}
