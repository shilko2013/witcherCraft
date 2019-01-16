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

    void saveThing(Thing thing, DescriptionThing descriptionThing, List<String> effectThings, Image image);

    void saveTypeThing(TypeThing typeThing);

    Optional<TypeThing> getTypeThingByName(String name);

    Optional<Thing> getThingById(Long id);

    Optional<Thing> getThingByName(String name);

    ResponseEntity addThing(String name,
                            int price,
                            double weight,
                            String description,
                            String type,
                            boolean isAlchemy,
                            List<String> effects,
                            MultipartFile imageFile);

    ResponseEntity addTypeThing(String name, String information);

    ResponseEntity editThing(String name,
                            int price,
                            double weight,
                            String description,
                            String type,
                            boolean isAlchemy,
                            List<String> effects,
                            MultipartFile imageFile);

    ResponseEntity editTypeThing(String name, String information);

    ResponseEntity<String> deleteTypeThing(Long id);

    void deleteThing(Long id);
}
