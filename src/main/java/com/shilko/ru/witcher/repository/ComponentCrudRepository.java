package com.shilko.ru.witcher.repository;

import com.shilko.ru.witcher.entity.CategoryComponent;
import com.shilko.ru.witcher.entity.Component;
import com.shilko.ru.witcher.entity.CraftOrAlchemy;
import com.shilko.ru.witcher.entity.Draft;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Component crud repository.
 */
public interface ComponentCrudRepository extends CrudRepository<Component, Long> {

    @Override
    Optional<Component> findById(Long id);

    @Override
    Component save(Component component);

    @Override
    void deleteById(Long id);

    @Override
    List<Component> findAll();

    List<Component> findAllByIsAlchemy(CraftOrAlchemy alchemy);

    /**
     * Find by name component.
     *
     * @param name the name
     * @return the component
     */
    Component findByName(String name);

    /**
     * Find all by category component equals list.
     *
     * @param categoryComponent the category component
     * @return the list
     */
    List<Component> findAllByCategoryComponentEquals(CategoryComponent categoryComponent);

    /**
     * Find all by drafts contains list.
     *
     * @param draft the draft
     * @return the list
     */
    List<Component> findAllByDraftsContains(Draft draft);
}
