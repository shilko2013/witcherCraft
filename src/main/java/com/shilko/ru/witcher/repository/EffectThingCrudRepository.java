package com.shilko.ru.witcher.repository;

import com.shilko.ru.witcher.entity.EffectThing;
import com.shilko.ru.witcher.entity.Thing;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Effect thing crud repository.
 */
public interface EffectThingCrudRepository extends CrudRepository<EffectThing, Long> {

    @Override
    Optional<EffectThing> findById(Long id);

    @Override
    EffectThing save(EffectThing effectThing);

    @Override
    @Modifying
    @Query("delete from EffectThing t where t.id = ?1")
    void deleteById(Long id);

    @Override
    List<EffectThing> findAll();

    /**
     * Find all by name list.
     *
     * @param name the name
     * @return the list
     */
    List<EffectThing> findAllByName(String name);

    /**
     * Find all by thing list.
     *
     * @param thing the thing
     * @return the list
     */
    List<EffectThing> findAllByThing(Thing thing);
}
