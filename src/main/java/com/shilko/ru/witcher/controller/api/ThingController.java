package com.shilko.ru.witcher.controller.api;

import com.google.gson.Gson;
import com.shilko.ru.witcher.entity.DescriptionThing;
import com.shilko.ru.witcher.entity.Image;
import com.shilko.ru.witcher.entity.Thing;
import com.shilko.ru.witcher.entity.TypeThing;
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

@RestController
@RequestMapping("/api/thing")
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
            thing.setDrafts(null);
            thing.getDescriptionThing().setThing(null);
            thing.getTypeThing().setThings(null);
        });
        return ResponseEntity.ok(new Gson().toJson(things));
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
        return ResponseEntity.ok(new Gson().toJson(thing.get()));
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
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity addThing(@RequestParam("name") String name,
                            @RequestParam("price") int price,
                            @RequestParam("weight") double weight,
                            @RequestParam("description") String description,
                            @RequestParam("typeId") long typeId,
                            @RequestParam("isAlchemy") boolean isAlchemy,
                            @RequestParam("effects") List<String> effects,
                            @RequestParam("effectsNames") List<String> effectsNames,
                            @RequestParam("image") MultipartFile imageFile) {
        if (thingService.getThingByName(name).isPresent())
            return ResponseEntity.badRequest().body("This thing already exists");
        return thingService.addThing(name, price, weight, description, typeId, isAlchemy, effects, effectsNames, imageFile);
    }

    @Transactional
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity editThing(@RequestParam("name") String name,
                            @RequestParam("price") int price,
                            @RequestParam("weight") double weight,
                            @RequestParam("description") String description,
                            @RequestParam("typeId") long typeId,
                            @RequestParam("isAlchemy") boolean isAlchemy,
                            @RequestParam("effects") List<String> effects,
                            @RequestParam("effectsNames") List<String> effectsNames,
                            @RequestParam("image") MultipartFile imageFile) {
        return thingService.addThing(name, price, weight, description, typeId, isAlchemy, effects, effectsNames, imageFile);
    }

    @Transactional
    @RequestMapping(value = "/addtype", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity addTypeThing(@RequestParam("name") String name,
                                @RequestParam("information") String information) {
        return thingService.addTypeThing(name,information,true);
    }

    @Transactional
    @RequestMapping(value = "/edittype", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity editTypeThing(@RequestParam("name") String name,
                                @RequestParam("information") String information) {
        return thingService.addTypeThing(name,information,false);
    }
}
