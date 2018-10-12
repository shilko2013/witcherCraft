package com.shilko.ru.wither.repository;

import com.shilko.ru.wither.entity.EffectThing;
import com.shilko.ru.wither.entity.Thing;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EffectThingCrudRepository extends CrudRepository<EffectThing, Long> {

    @Override
    Optional<EffectThing> findById(Long id);

    @Override
    EffectThing save(EffectThing effectThing);

    @Override
    void deleteById(Long id);

    @Override
    List<EffectThing> findAll();

    EffectThing findByName(String name);

    List<EffectThing> findAllByThing(Thing thing);
}
