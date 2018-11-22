package com.shilko.ru.witcher.repository;

import com.shilko.ru.witcher.entity.Thing;
import com.shilko.ru.witcher.entity.TypeThing;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Type thing crud repository.
 */
public interface TypeThingCrudRepository extends CrudRepository<TypeThing, Long> {

    Optional<TypeThing> findById(Long id);

    /**
     * Find by name type thing.
     *
     * @param name the name
     * @return the type thing
     */
    Optional<TypeThing> findByName(String name);

    @Override
    TypeThing save(TypeThing typeThing);

    @Override
    void deleteById(Long id);

    /**
     * Find by things contains list.
     *
     * @param thing the thing
     * @return the list
     */
    List<TypeThing> findByThingsContains(Thing thing);
}
