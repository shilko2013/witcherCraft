package com.shilko.ru.witcher.controller.api;

import com.google.gson.Gson;
import com.shilko.ru.witcher.entity.Component;
import com.shilko.ru.witcher.entity.Draft;
import com.shilko.ru.witcher.entity.Image;
import com.shilko.ru.witcher.entity.Thing;
import com.shilko.ru.witcher.service.DraftService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/draft")
public class DraftController {

    @Autowired
    private DraftService draftService;

    @Value("classpath:standard_picture.png")
    Resource standardImage;

    @RequestMapping(value = "/drafts/{isAlchemy}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity getAllDrafts(@PathVariable boolean isAlchemy) {
        List<Draft> drafts = draftService.getAllDrafts(isAlchemy);
        drafts.forEach(draft -> {
            Thing thing = new Thing();
            thing.setId(draft.getThing().getId());
            thing.setName(draft.getThing().getName());
            draft.setThing(thing);
            List<Component> components = new ArrayList<>();
            draft.getComponents().forEach(component -> {
                Component newComponent = new Component();
                newComponent.setId(component.getId());
                newComponent.setName(component.getName());
            });
            draft.setComponents(components);
        });
        return ResponseEntity.ok(new Gson().toJson(drafts));
    }

    @RequestMapping(value = "/{strId}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity getDraft(@PathVariable String strId) {
        long id;
        try {
            id = Long.parseLong(strId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal id");
        }
        Optional<Draft> draft = draftService.getDraftById(id);
        if (!draft.isPresent()) {
            return ResponseEntity.badRequest().body("Components doesn't present");
        }
        Thing thing = new Thing();
        thing.setId(draft.get().getThing().getId());
        thing.setName(draft.get().getThing().getName());
        draft.get().setThing(thing);
        List<Component> components = new ArrayList<>();
        draft.get().getComponents().forEach(component -> {
            Component newComponent = new Component();
            newComponent.setId(component.getId());
            newComponent.setName(component.getName());
            components.add(newComponent);
        });
        draft.get().setComponents(components);
        return ResponseEntity.ok(new Gson().toJson(draft.get()));
    }

    @Transactional
    @RequestMapping(value = "/{strId}/image", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity getImageByIdDraft(@PathVariable String strId) {
        long id;
        final HttpHeaders headers = new HttpHeaders();
        try {
            id = Long.parseLong(strId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal id");
        }
        Optional<Image> image = draftService.getImageByIdDraft(id);
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
    ResponseEntity addDraft(@RequestParam("information") String information,
                            @RequestParam("thingId") String thingId,
                            @RequestParam("componentId") List<Long> componentId) {
        try {
            draftService.saveDraft(information, thingId, componentId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal set of arguments");
        }
        return ResponseEntity.ok("Successfully added");
    }
}
