package com.shilko.ru.wither.repository;

import com.shilko.ru.wither.entity.DescriptionThing;
import com.shilko.ru.wither.entity.Thing;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DescriptionThingCrudRepository extends CrudRepository<DescriptionThing, Long> {
    @Override
    Optional<DescriptionThing> findById(Long id);

    @Override
    DescriptionThing save(DescriptionThing descriptionThing);

    @Override
    void deleteById(Long id);

    @Override
    List<DescriptionThing> findAll();

    DescriptionThing findByThing(Thing component);

}
