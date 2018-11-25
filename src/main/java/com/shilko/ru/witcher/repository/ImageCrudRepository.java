package com.shilko.ru.witcher.repository;

import com.shilko.ru.witcher.entity.Component;
import com.shilko.ru.witcher.entity.Image;
import com.shilko.ru.witcher.entity.Thing;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ImageCrudRepository extends CrudRepository<Image, Long> {
    Optional<Image> findByComponent(Component component);
    Optional<Image> findByThing(Thing thing);
}