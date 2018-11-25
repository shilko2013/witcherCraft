package com.shilko.ru.witcher.service;

import com.shilko.ru.witcher.entity.*;

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
}
