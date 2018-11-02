package com.shilko.ru.witcher.repository;

import com.shilko.ru.witcher.entity.DescriptionThing;
import com.shilko.ru.witcher.entity.Thing;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Description thing crud repository.
 */
public interface DescriptionThingCrudRepository extends CrudRepository<DescriptionThing, Long> {
    @Override
    Optional<DescriptionThing> findById(Long id);

    @Override
    DescriptionThing save(DescriptionThing descriptionThing);

    @Override
    void deleteById(Long id);

    @Override
    List<DescriptionThing> findAll();

    /**
     * Find by thing description thing.
     *
     * @param component the component
     * @return the description thing
     */
    DescriptionThing findByThing(Thing component);

}
