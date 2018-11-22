package com.shilko.ru.witcher.repository;

import com.shilko.ru.witcher.entity.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageCrudRepository extends CrudRepository<Image, Long> { }