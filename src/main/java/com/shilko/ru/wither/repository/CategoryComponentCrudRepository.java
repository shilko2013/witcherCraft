package com.shilko.ru.wither.repository;

import com.shilko.ru.wither.entity.CategoryComponent;
import com.shilko.ru.wither.entity.Component;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryComponentCrudRepository extends CrudRepository<CategoryComponent, Long> {

    @Override
    Optional<CategoryComponent> findById(Long id);

    CategoryComponent findByName(String name);

    @Override
    CategoryComponent save(CategoryComponent categoryComponent);

    @Override
    void deleteById(Long id);

    @Override
    List<CategoryComponent> findAll();

    CategoryComponent findByComponentsContains(Component component);
}
