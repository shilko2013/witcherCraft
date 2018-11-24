package com.shilko.ru.witcher.repository;

import com.shilko.ru.witcher.entity.CategoryComponent;
import com.shilko.ru.witcher.entity.Component;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Category component crud repository.
 */
public interface CategoryComponentCrudRepository extends CrudRepository<CategoryComponent, Long> {

    @Override
    Optional<CategoryComponent> findById(Long id);

    /**
     * Find by name category component.
     *
     * @param name the name
     * @return the category component
     */
    Optional<CategoryComponent> findByName(String name);

    @Override
    CategoryComponent save(CategoryComponent categoryComponent);

    @Override
    void deleteById(Long id);

    @Override
    List<CategoryComponent> findAll();

    /**
     * Find by components contains category component.
     *
     * @param component the component
     * @return the category component
     */
    /*Optional<CategoryComponent> findByComponentsContains(Component component);*/
}
