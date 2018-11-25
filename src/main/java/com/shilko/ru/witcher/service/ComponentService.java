package com.shilko.ru.witcher.service;

import com.shilko.ru.witcher.entity.CategoryComponent;
import com.shilko.ru.witcher.entity.Component;
import com.shilko.ru.witcher.entity.DescriptionComponent;
import com.shilko.ru.witcher.entity.Image;

import java.util.List;
import java.util.Optional;

public interface ComponentService {

    List<Component> getAllComponents();

    Optional<Image> getImageByIdComponent(Long id);

    Optional<CategoryComponent> getCategoryComponentById(Long id);

    void saveComponent(Component component, Image image, DescriptionComponent descriptionComponent);

    void saveCategoryComponent(CategoryComponent categoryComponent);

    Optional<CategoryComponent> getCategoryComponentByName(String name);

    Optional<Component> getComponentById(Long id);
}
