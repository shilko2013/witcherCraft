package com.shilko.ru.wither.repository;

import com.shilko.ru.wither.entity.Thing;
import com.shilko.ru.wither.entity.TypeThing;
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
    TypeThing findByName(String name);

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
