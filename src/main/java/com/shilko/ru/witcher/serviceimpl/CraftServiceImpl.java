package com.shilko.ru.witcher.serviceimpl;

import com.shilko.ru.witcher.entity.Thing;
import com.shilko.ru.witcher.repository.ThingCrudRepository;
import com.shilko.ru.witcher.service.CraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CraftServiceImpl implements CraftService {

    @Autowired
    private ThingCrudRepository thingCrudRepository;

    @Override
    public Thing getTreeByThingId(Long id) {
        Thing thing = thingCrudRepository.findById(id).get();
        thing.getEffects().forEach(effectThing -> effectThing.setThing(null));
        thing.setImage(null);
        thing.getTypeThing().setThings(null);
        thing.getDescriptionThing().setThing(null);
        thing.getDrafts().forEach(draft -> {
            draft.setThing(null);
            draft.getComponents().forEach(component -> {
                component.setDrafts(null);
                component.getDescriptionComponent().setComponent(null);
                component.setImage(null);
            });
        });
        return thing;
    }
}
