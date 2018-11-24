package com.shilko.ru.witcher.repository;

import com.shilko.ru.witcher.entity.Component;
import com.shilko.ru.witcher.entity.Image;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ImageCrudRepository extends CrudRepository<Image, Long> {
    Optional<Image> findByComponent(Component component);
}