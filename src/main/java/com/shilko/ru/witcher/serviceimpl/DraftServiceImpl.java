package com.shilko.ru.witcher.serviceimpl;

import com.shilko.ru.witcher.entity.Component;
import com.shilko.ru.witcher.entity.Draft;
import com.shilko.ru.witcher.entity.Image;
import com.shilko.ru.witcher.entity.Thing;
import com.shilko.ru.witcher.repository.ComponentCrudRepository;
import com.shilko.ru.witcher.repository.DraftCrudRepository;
import com.shilko.ru.witcher.repository.ImageCrudRepository;
import com.shilko.ru.witcher.repository.ThingCrudRepository;
import com.shilko.ru.witcher.service.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.x509.DeltaCRLIndicatorExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DraftServiceImpl implements DraftService {

    @Autowired
    private ThingCrudRepository thingCrudRepository;

    @Autowired
    private ComponentCrudRepository componentCrudRepository;

    @Autowired
    private DraftCrudRepository draftCrudRepository;

    @Autowired
    private ImageCrudRepository imageCrudRepository;

    @Override
    public List<Draft> getAllDrafts(boolean isAlchemy) {
       return draftCrudRepository.findAllByThing_IsAlchemy(isAlchemy);
    }

    @Override
    public Optional<Image> getImageByIdDraft(Long id) {
        Optional<Draft> draft = draftCrudRepository.findById(id);
        if (!draft.isPresent())
            return Optional.empty();
        Optional<Thing> thing = thingCrudRepository.findByDraftsContains(draft.get());
        if (!thing.isPresent())
            return Optional.empty();
        return imageCrudRepository.findByThing(thing.get());
    }

    @Transactional
    @Override
    public void saveDraft(String information, String thingId, List<Long> componentId) {
        List<Component> components = new ArrayList<>();
        componentId.forEach(component -> components.add(componentCrudRepository.findById(component).get()));
        Draft draft = new Draft(thingCrudRepository.findById(Long.parseLong(thingId)).get(), information, components);
        draftCrudRepository.save(draft);
    }

    @Override
    public Optional<Draft> getDraftById(Long id) {
        return draftCrudRepository.findById(id);
    }
}
