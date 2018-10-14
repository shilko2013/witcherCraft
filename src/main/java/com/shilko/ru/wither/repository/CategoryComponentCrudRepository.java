package com.shilko.ru.wither.repository;

import com.shilko.ru.wither.entity.CategoryComponent;
import com.shilko.ru.wither.entity.Component;
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
    CategoryComponent findByName(String name);

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
    CategoryComponent findByComponentsContains(Component component);
}
