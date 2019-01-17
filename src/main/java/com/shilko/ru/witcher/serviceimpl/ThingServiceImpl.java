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

    private static final long MAX_FILE_SIZE = 0xA00000; //10Mb

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
    public void saveThing(Thing thing, DescriptionThing descriptionThing, List<String> effects, Image image) {
        if (image != null)
            imageCrudRepository.save(image);
        descriptionThingCrudRepository.save(descriptionThing);
        thing.setDescriptionThing(descriptionThing);
        thing.setImage(image);
        thing.setEffects(null);
        thingCrudRepository.save(thing);
        List<EffectThing> effectThings = new ArrayList<>();
        for (int i = 0; i < effects.size(); ++i)
            effectThings.add(new EffectThing(effects.get(i), "", thing));
        effectThings.forEach(effectThing -> effectThingCrudRepository.save(effectThing));
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
                                   String type,
                                   boolean isAlchemy,
                                   List<String> effects,
                                   MultipartFile imageFile) {
        DescriptionThing descriptionThing = new DescriptionThing(description);
        TypeThing typeThing = getTypeThingByName(type).orElse(null);
        if (typeThing == null) {
            typeThing = new TypeThing(type, "");
            typeThingCrudRepository.save(typeThing);
        }
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
        Thing thing = new Thing(name, price, weight, typeThing, descriptionThing, isAlchemy, image);
        try {
            saveThing(thing, descriptionThing, effects, image);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal set of arguments");
        }
        return ResponseEntity.ok("Successfully added");
    }

    @Override
    public ResponseEntity addTypeThing(String name, String information) {
        if (name == null || information == null)
            return ResponseEntity.badRequest().body("Illegal set of arguments");
        try {
            if (getTypeThingByName(name).isPresent())
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
        thingCrudRepository.deleteById(id);
        if (thing.get().getImage() != null)
        imageCrudRepository.delete(thing.get().getImage());
        descriptionThingCrudRepository.delete(thing.get().getDescriptionThing());
    }

    @Override
    public ResponseEntity editThing(String name,
                                    int price,
                                    double weight,
                                    String description,
                                    String type,
                                    List<String> effects,
                                    MultipartFile imageFile) {
        try {
            Thing thing = thingCrudRepository.findByName(name).get();
            thing.getEffects().stream().map(EffectThing::getId).forEach(effectThingCrudRepository::deleteById);
            Image oldImage = imageCrudRepository.findByThing(thing).orElse(null);
            thing.setPrice(price);
            thing.setWeight(weight);
            DescriptionThing descriptionThing = descriptionThingCrudRepository.findByThing(thing).get();
            descriptionThing.setDescription(description);
            descriptionThingCrudRepository.save(descriptionThing);
            if (!typeThingCrudRepository.findByName(type).isPresent())
                typeThingCrudRepository.save(new TypeThing(type,""));
            thing.setTypeThing(typeThingCrudRepository.findByName(type).get()); //!!! Exception!!!
            /*List<EffectThing> effectThings = new ArrayList<>();
            for (int i = 0; i < effects.size(); ++i)
                effectThings.add(new EffectThing(effects.get(i), "", thing));
            thing.setEffects(effectThings);*/
            Image image = new Image();
            String[] split;
            if (imageFile == null || imageFile.getSize() == 0)
                image = oldImage;
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
            /*thing.getImage().setType(image.getType());
            thing.getImage().setPicture(image.getPicture());*/
            thing.setImage(image);
            if (image != oldImage)
                imageCrudRepository.save(image);
            saveThing(thing,descriptionThing,effects,image);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal set of arguments");
        }
        return ResponseEntity.ok("Successfully edited");
    }

    @Override
    public ResponseEntity editTypeThing(String name, String information) {
        try {
            TypeThing typeThing = typeThingCrudRepository.findByName(name).get();
            typeThing.setInformation(information);
            typeThingCrudRepository.save(typeThing);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal set of arguments");
        }
        return ResponseEntity.ok("Successfully edited");
    }
}
