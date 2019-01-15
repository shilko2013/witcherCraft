package com.shilko.ru.witcher.controller.api;

import com.shilko.ru.witcher.entity.*;
import com.shilko.ru.witcher.service.ComponentService;
import org.apache.commons.codec.binary.Base64;
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
@RequestMapping("/component")
public class ComponentController { //TODO edit & delete

    @Autowired
    private ComponentService componentService;

    @Value("classpath:standard_picture.png")
    Resource standardImage;

    @RequestMapping(value = "/components/{isAlchemy}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity getAllComponents(@PathVariable boolean isAlchemy) {
        List<Component> components = componentService.getAllComponents(isAlchemy);
        components.forEach(component -> {
            component.setImage(null);
            component.getDescriptionComponent().setComponent(null);
            component.setDrafts(null);
        });
        return ResponseEntity.ok(components);
    }

    @RequestMapping(value = "/{strId}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity getComponent(@PathVariable String strId) {
        long id;
        try {
            id = Long.parseLong(strId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal id");
        }
        Optional<Component> component = componentService.getComponentById(id);
        if (!component.isPresent()) {
            return ResponseEntity.badRequest().body("Components doesn't present");
        }
        component.get().setImage(null);
        component.get().getDescriptionComponent().setComponent(null);
        component.get().getDrafts().forEach(draft -> {
            Thing thing = new Thing();
            thing.setName(draft.getThing().getName());
            draft.setThing(thing);
            draft.setComponents(null);
        });
        return ResponseEntity.ok(component.get());
    }

    @Transactional
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
        return new ResponseEntity<>(Base64.decodeBase64(image.get().getPicture()), headers, HttpStatus.OK);
    }

    @Transactional
    @RequestMapping(value = "/api/add", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity addComponent(@RequestParam("name") String name,
                                @RequestParam("price") int price,
                                @RequestParam("weight") double weight,
                                @RequestParam("description") String description,
                                @RequestParam("categoryId") long categoryId,
                                @RequestParam("isAlchemy") boolean isAlchemy,
                                @RequestParam(value="image", required=false) MultipartFile imageFile) {
        if (componentService.getComponentByName(name).isPresent())
            return ResponseEntity.badRequest().body("This component already exists");
        return componentService.addComponent(name, price, weight, description, categoryId, isAlchemy, imageFile);
    }

    @Transactional
    @RequestMapping(value = "/api/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity editComponent(@RequestParam("name") String name,
                                 @RequestParam("price") int price,
                                 @RequestParam("weight") double weight,
                                 @RequestParam("description") String description,
                                 @RequestParam("category") String category,
                                 @RequestParam(value="image", required=false) MultipartFile imageFile) {
        return componentService.editComponent(name, price, weight, description, category, imageFile);
    }

    @Transactional
    @RequestMapping(value = "/api/addcategory", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity addCategoryComponent(@RequestParam("name") String name,
                                        @RequestParam("information") String information) {
        return componentService.addCategoryComponent(name, information);
    }

    @Transactional
    @RequestMapping(value = "/api/editcategory", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity editCategoryComponent(@RequestParam("name") String name,
                                        @RequestParam("information") String information) {
        return componentService.editCategoryComponent(name, information);
    }

    @Transactional
    @RequestMapping(value = "/api/deletecategory/{strId}", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity deleteCategoryComponent(@PathVariable String strId) {
        try {
            return componentService.deleteCategoryComponent(Long.parseLong(strId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal id");
        }
    }

    @Transactional
    @RequestMapping(value = "/api/delete/{strId}", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity deleteComponent(@PathVariable String strId) {
        try {
            componentService.deleteComponent(Long.parseLong(strId));
            return ResponseEntity.ok("Component deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal id");
        }
    }
}
