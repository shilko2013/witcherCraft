package com.shilko.ru.wither.repository;

import com.shilko.ru.wither.entity.Component;
import com.shilko.ru.wither.entity.Draft;
import com.shilko.ru.wither.entity.Thing;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DraftCrudRepository extends CrudRepository<Draft, Long> {

    @Override
    Optional<Draft> findById(Long id);

    @Override
    Draft save(Draft draft);

    @Override
    void deleteById(Long id);

    @Override
    List<Draft> findAll();

    List<Draft> findAllByComponentsContains(Component component);

    List<Draft> findAllByThing(Thing thing);
}
