package com.shilko.ru.witcher.repository;

import com.shilko.ru.witcher.entity.Component;
import com.shilko.ru.witcher.entity.DescriptionComponent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Description component crud repository.
 */
public interface DescriptionComponentCrudRepository extends CrudRepository<DescriptionComponent, Long> {

    @Override
    Optional<DescriptionComponent> findById(Long id);

    @Override
    DescriptionComponent save(DescriptionComponent descriptionComponent);

    @Override
    void deleteById(Long id);

    @Override
    List<DescriptionComponent> findAll();

    /**
     * Find by component description component.
     *
     * @param component the component
     * @return the description component
     */
    Optional<DescriptionComponent> findByComponent(Component component);
}
