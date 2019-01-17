package com.shilko.ru.witcher.controller.api;

import com.shilko.ru.witcher.entity.*;
import com.shilko.ru.witcher.service.ThingService;
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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/thing")
public class ThingController {

    @Autowired
    private ThingService thingService;

    @Value("classpath:standard_picture.png")
    Resource standardImage;

    private final static long MAX_FILE_SIZE = 0xA00000; //10Mb

    @RequestMapping(value = "/things/{isAlchemy}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity getAllThings(@PathVariable boolean isAlchemy) {
        List<Thing> things = thingService.getAllThings(isAlchemy);
        things.forEach(thing -> {
            thing.setImage(null);
            thing.getEffects().forEach(effectThing -> effectThing.setThing(null));
            thing.setDrafts(thing.getDrafts().stream().map(draft -> {
                Draft draft1 = new Draft();
                draft1.setId(draft.getId());
                return draft1;
            }).collect(Collectors.toList()));
            thing.getDescriptionThing().setThing(null);
            thing.getTypeThing().setThings(null);
        });
        return ResponseEntity.ok(things);
    }

    @RequestMapping(value = "/{strId}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity getThing(@PathVariable String strId) {
        long id;
        try {
            id = Long.parseLong(strId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal id");
        }
        Optional<Thing> thing = thingService.getThingById(id);
        if (!thing.isPresent()) {
            return ResponseEntity.badRequest().body("Components doesn't present");
        }
        thing.get().setImage(null);
        thing.get().getEffects().forEach(effectThing -> effectThing.setThing(null));
        thing.get().getDrafts().forEach(draft -> {
            draft.setThing(null);
            draft.setComponents(null);
        });
        thing.get().getDescriptionThing().setThing(null);
        thing.get().getTypeThing().setThings(null);
        return ResponseEntity.ok((thing.get()));
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
        Optional<Image> image = thingService.getImageByIdThing(id);
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
    ResponseEntity addThing(@RequestParam("name") String name,
                            @RequestParam("price") int price,
                            @RequestParam("weight") double weight,
                            @RequestParam("description") String description,
                            @RequestParam("type") String type,
                            @RequestParam("isAlchemy") boolean isAlchemy,
                            @RequestParam("effects") List<String> effects,
                            @RequestParam(value="image", required=false) MultipartFile imageFile) {
        if (thingService.getThingByName(name).isPresent())
            return ResponseEntity.badRequest().body("This thing already exists");
        return thingService.addThing(name, price, weight, description, type, isAlchemy, effects, imageFile);
    }

    @Transactional
    @RequestMapping(value = "/api/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity editThing(@RequestParam("name") String name,
                            @RequestParam("price") int price,
                            @RequestParam("weight") double weight,
                            @RequestParam("description") String description,
                            @RequestParam("type") String type,
                            @RequestParam("effects") List<String> effects,
                            @RequestParam(value="image", required=false) MultipartFile imageFile) {
        return thingService.editThing(name, price, weight, description, type, effects, imageFile);
    }

    @Transactional
    @RequestMapping(value = "/api/addtype", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity addTypeThing(@RequestParam("name") String name,
                                @RequestParam("information") String information) {
        return thingService.addTypeThing(name,information);
    }

    @Transactional
    @RequestMapping(value = "/api/edittype", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity editTypeThing(@RequestParam("name") String name,
                                @RequestParam("information") String information) {
        return thingService.editTypeThing(name,information);
    }

    @Transactional
    @RequestMapping(value = "/api/deletetype/{strId}", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity deleteCategoryComponent(@PathVariable String strId) {
        try {
            thingService.deleteTypeThing(Long.parseLong(strId));
            return ResponseEntity.ok("Type deleted");
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
            thingService.deleteThing(Long.parseLong(strId));
            return ResponseEntity.ok("Thing deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal id");
        }
    }
}
