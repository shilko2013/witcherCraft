package com.shilko.ru.witcher.serviceimpl;

import com.shilko.ru.witcher.entity.*;
import com.shilko.ru.witcher.repository.*;
import com.shilko.ru.witcher.service.ThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ThingServiceImpl implements ThingService {

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
            effectThings.add(new EffectThing(effectNames.get(i), effects.get(i),null));
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
}
