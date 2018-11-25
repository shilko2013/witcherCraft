package com.shilko.ru.witcher.service;

import com.shilko.ru.witcher.entity.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ThingService {

    List<Thing> getAllThings(boolean isAlchemy);

    Optional<Image> getImageByIdThing(Long id);

    Optional<TypeThing> getTypeThingById(Long id);

    void saveThing(Thing thing, DescriptionThing descriptionThing, List<String> effectThings, List<String> effectNames, Image image);

    void saveTypeThing(TypeThing typeThing);

    Optional<TypeThing> getTypeThingByName(String name);

    Optional<Thing> getThingById(Long id);

    Optional<Thing> getThingByName(String name);

    ResponseEntity addThing(String name,
                            int price,
                            double weight,
                            String description,
                            long typeId,
                            boolean isAlchemy,
                            List<String> effects,
                            List<String> effectsNames,
                            MultipartFile imageFile);

    ResponseEntity addTypeThing(String name, String information, boolean add);
}
