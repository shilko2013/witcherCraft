package com.shilko.ru.witcher.repository;

import com.shilko.ru.witcher.entity.Draft;
import com.shilko.ru.witcher.entity.Thing;
import com.shilko.ru.witcher.entity.TypeThing;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Thing crud repository.
 */
public interface ThingCrudRepository extends CrudRepository<Thing, Long> {

    @Override
    Optional<Thing> findById(Long id);

    @Override
    Thing save(Thing thing);

    @Override
    void deleteById(Long id);

    @Override
    List<Thing> findAll();

    List<Thing> findAllByIsAlchemy(boolean alchemy);


    /**
     * Find by drafts contains thing.
     *
     * @param draft the draft
     * @return the thing
     */
    Optional<Thing> findByDraftsContains(Draft draft);

    /**
     * Find all by type thing list.
     *
     * @param typeThing the type thing
     * @return the list
     */
    List<Thing> findAllByTypeThing(TypeThing typeThing);

    Optional<Thing> findByName(String name);
}
