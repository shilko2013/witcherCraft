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
    public List<Component> getAllComponents() {
        return componentCrudRepository.findAll();
    }

    @Override
    public Optional<Image> getImageByIdComponent(Long id) {
        return imageCrudRepository.findByComponent_Id(id);
    }

    @Override
    public Optional<CategoryComponent> getCategoryComponentById(Long id) {
        return categoryComponentCrudRepository.findById(id);
    }

    @Override
    public void saveComponent(Component component, Image image, DescriptionComponent descriptionComponent) {
        descriptionComponentCrudRepository.save(descriptionComponent);
        componentCrudRepository.save(component);
        imageCrudRepository.save(image);
    }

    @Override
    public void saveCategoryComponent(CategoryComponent categoryComponent) {
        categoryComponentCrudRepository.save(categoryComponent);
    }

    @Override
    public Optional<CategoryComponent> getCategoryComponentByName(String name) {
        return categoryComponentCrudRepository.findByName(name);
    }
}
