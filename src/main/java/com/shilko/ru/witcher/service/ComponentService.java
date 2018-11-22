package com.shilko.ru.witcher.service;

import com.shilko.ru.witcher.entity.Component;
import com.shilko.ru.witcher.entity.Image;

import java.util.List;
import java.util.Optional;

public interface ComponentService {

    List<Component> getAllComponents();

    Optional<Image> getImageByIdComponent(Long id);
}
