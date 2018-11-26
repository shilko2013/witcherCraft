package com.shilko.ru.witcher.serviceimpl;

import com.shilko.ru.witcher.config.ApplicationConfigController;
import com.shilko.ru.witcher.entity.*;
import com.shilko.ru.witcher.repository.*;
import com.shilko.ru.witcher.service.ThingService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ThingServiceImpl implements ThingService {

    private static final long MAX_FILE_SIZE = ApplicationConfigController.getMaxFileSize();

    @Autowired
    private TypeThingCrudRepository typeThingCrudRepository;

    @Autowired
    private ImageCrudRepository imageCrudRepository;

    @Autowired
    private ThingCrudRepository thingCrudRepository;

    @Autowired
    private EffectThingCrudRepository effectThingCrudRepository;

    @Autowired
    private DescriptionThingCrudRepository descriptionThingCrudRepository;

    @Autowired
    private DraftCrudRepository draftCrudRepository;

    @Override
    public List<Thing> getAllThings(boolean isAlchemy) {
        return thingCrudRepository.findAllByIsAlchemy(isAlchemy);
    }

    @Override
    public Optional<Image> getImageByIdThing(Long id) {
        Optional<Thing> thing = thingCrudRepository.findById(id);
        if (!thing.isPresent())
            return Optional.empty();
        return imageCrudRepository.findByThing(thing.get());
    }

    @Override
    public Optional<TypeThing> getTypeThingById(Long id) {
        return typeThingCrudRepository.findById(id);
    }

    @Transactional
    @Override
    public void saveThing(Thing thing, DescriptionThing descriptionThing, List<String> effects, List<String> effectNames, Image image) {
        List<EffectThing> effectThings = new ArrayList<>();
        for (int i = 0; i < effects.size(); ++i)
            effectThings.add(new EffectThing(effectNames.get(i), effects.get(i), null));
        effectThings.forEach(effectThing -> effectThingCrudRepository.save(effectThing));
        if (image != null)
            imageCrudRepository.save(image);
        descriptionThingCrudRepository.save(descriptionThing);
        thing.setDescriptionThing(descriptionThing);
        thing.setEffects(effectThings);
        thing.setImage(image);
        thingCrudRepository.save(thing);
    }

    @Override
    public void saveTypeThing(TypeThing typeThing) {
        typeThingCrudRepository.save(typeThing);
    }

    @Override
    public Optional<TypeThing> getTypeThingByName(String name) {
        return typeThingCrudRepository.findByName(name);
    }

    @Override
    public Optional<Thing> getThingById(Long id) {
        return thingCrudRepository.findById(id);
    }

    @Override
    public Optional<Thing> getThingByName(String name) {
        return thingCrudRepository.findByName(name);
    }

    @Override
    public ResponseEntity addThing(String name,
                                   int price,
                                   double weight,
                                   String description,
                                   long typeId,
                                   boolean isAlchemy,
                                   List<String> effects,
                                   List<String> effectsNames,
                                   MultipartFile imageFile) {
        DescriptionThing descriptionThing = new DescriptionThing(description);
        Optional<TypeThing> typeThing = getTypeThingById(typeId);
        if (!typeThing.isPresent())
            return ResponseEntity.badRequest().body("Illegal category id");
        Image image = new Image();
        String[] split;
        if (imageFile == null || imageFile.getSize() == 0)
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
                image.setPicture(Base64.encodeBase64String(imageFile.getBytes()));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Illegal image file");
            }
        }
        Thing thing = new Thing(name, price, weight, typeThing.get(), descriptionThing, isAlchemy, image);
        try {
            saveThing(thing, descriptionThing, effects, effectsNames, image);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal set of arguments");
        }
        return ResponseEntity.ok("Successfully added");
    }

    @Override
    public ResponseEntity addTypeThing(String name, String information, boolean add) {
        if (name == null || information == null)
            return ResponseEntity.badRequest().body("Illegal set of arguments");
        try {
            if (add && getTypeThingByName(name).isPresent())
                return ResponseEntity.badRequest().body("This category already exists");
            saveTypeThing(new TypeThing(name, information));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal set of arguments");
        }
        return ResponseEntity.ok("Successfully added");
    }

    @Override
    public ResponseEntity<String> deleteTypeThing(Long id) {
        if (!thingCrudRepository.findAllByTypeThingEquals(typeThingCrudRepository.findById(id).get()).isEmpty())
            return ResponseEntity.badRequest().body("Can not delete - thing with this category exists");
        thingCrudRepository.deleteById(id);
        return ResponseEntity.ok("Type deleted");
    }

    @Override
    public void deleteThing(Long id) {
        Optional<Thing> thing = thingCrudRepository.findById(id);
        imageCrudRepository.delete(thing.get().getImage());
        thing.get().getDrafts().forEach(draftCrudRepository::delete);
        thing.get().getEffects().forEach(effectThingCrudRepository::delete);
        descriptionThingCrudRepository.delete(thing.get().getDescriptionThing());
    }
}
