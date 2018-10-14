package com.shilko.ru.wither.repository;

import com.shilko.ru.wither.entity.Component;
import com.shilko.ru.wither.entity.Draft;
import com.shilko.ru.wither.entity.Thing;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Draft crud repository.
 */
public interface DraftCrudRepository extends CrudRepository<Draft, Long> {

    @Override
    Optional<Draft> findById(Long id);

    @Override
    Draft save(Draft draft);

    @Override
    void deleteById(Long id);

    @Override
    List<Draft> findAll();

    /**
     * Find all by components contains list.
     *
     * @param component the component
     * @return the list
     */
    List<Draft> findAllByComponentsContains(Component component);

    /**
     * Find all by thing list.
     *
     * @param thing the thing
     * @return the list
     */
    List<Draft> findAllByThing(Thing thing);
}
