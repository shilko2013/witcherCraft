package com.shilko.ru.wither.repository;

import com.shilko.ru.wither.entity.Draft;
import com.shilko.ru.wither.entity.Thing;
import com.shilko.ru.wither.entity.TypeThing;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ThingCrudRepository extends CrudRepository<Thing, Long> {

    @Override
    Optional<Thing> findById(Long id);

    @Override
    Thing save(Thing thing);

    @Override
    void deleteById(Long id);

    @Override
    List<Thing> findAll();

    Thing findByDraftsContains(Draft draft);

    List<Thing> findAllByTypeThing(TypeThing typeThing);
}
