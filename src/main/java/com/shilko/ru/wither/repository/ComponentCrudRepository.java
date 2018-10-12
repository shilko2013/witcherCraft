package com.shilko.ru.wither.repository;

import com.shilko.ru.wither.entity.CategoryComponent;
import com.shilko.ru.wither.entity.Component;
import com.shilko.ru.wither.entity.Draft;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ComponentCrudRepository extends CrudRepository<Component, Long> {

    @Override
    Optional<Component> findById(Long id);

    @Override
    Component save(Component component);

    @Override
    void deleteById(Long id);

    @Override
    List<Component> findAll();

    Component findByName(String name);

    List<Component> findAllByCategoryComponentEquals(CategoryComponent categoryComponent);

    List<Component> findAllByDraftsContains(Draft draft);
}
