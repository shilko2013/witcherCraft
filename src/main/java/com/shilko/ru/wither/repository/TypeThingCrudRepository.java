package com.shilko.ru.wither.repository;

import com.shilko.ru.wither.entity.Thing;
import com.shilko.ru.wither.entity.TypeThing;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TypeThingCrudRepository extends CrudRepository<TypeThing, Long> {

    Optional<TypeThing> findById(Long id);

    TypeThing findByName(String name);

    @Override
    TypeThing save(TypeThing typeThing);

    @Override
    void deleteById(Long id);

    TypeThing findByThing(Thing thing);
}
