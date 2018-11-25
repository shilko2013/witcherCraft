package com.shilko.ru.witcher.serviceimpl;

import com.shilko.ru.witcher.entity.CategoryComponent;
import com.shilko.ru.witcher.entity.Component;
import com.shilko.ru.witcher.entity.DescriptionComponent;
import com.shilko.ru.witcher.entity.Image;
import com.shilko.ru.witcher.repository.CategoryComponentCrudRepository;
import com.shilko.ru.witcher.repository.ComponentCrudRepository;
import com.shilko.ru.witcher.repository.DescriptionComponentCrudRepository;
import com.shilko.ru.witcher.repository.ImageCrudRepository;
import com.shilko.ru.witcher.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComponentServiceImpl implements ComponentService {

    @Autowired
    private ComponentCrudRepository componentCrudRepository;

    @Autowired
    private CategoryComponentCrudRepository categoryComponentCrudRepository;

    @Autowired
    private ImageCrudRepository imageCrudRepository;

    @Autowired
    private DescriptionComponentCrudRepository descriptionComponentCrudRepository;

    @Override
    public List<Component> getAllComponents(boolean isAlchemy) {
        return componentCrudRepository.findAllByIsAlchemy(isAlchemy);
    }

    @Override
    public Optional<Image> getImageByIdComponent(Long id) {
        Optional<Component> component = componentCrudRepository.findById(id);
        if (!component.isPresent())
            return Optional.empty();
        return imageCrudRepository.findByComponent(component.get());
    }

    @Override
    public Optional<CategoryComponent> getCategoryComponentById(Long id) {
        return categoryComponentCrudRepository.findById(id);
    }

    @Override
    public void saveComponent(Component component, Image image, DescriptionComponent descriptionComponent) {
        descriptionComponentCrudRepository.save(descriptionComponent);
        component.setDescriptionComponent(descriptionComponent);
        imageCrudRepository.save(image);
        component.setImage(image);
        componentCrudRepository.save(component);
    }

    @Override
    public void saveCategoryComponent(CategoryComponent categoryComponent) {
        categoryComponentCrudRepository.save(categoryComponent);
    }

    @Override
    public Optional<CategoryComponent> getCategoryComponentByName(String name) {
        return categoryComponentCrudRepository.findByName(name);
    }

    @Override
    public Optional<Component> getComponentById(Long id) {
        return componentCrudRepository.findById(id);
    }
}
