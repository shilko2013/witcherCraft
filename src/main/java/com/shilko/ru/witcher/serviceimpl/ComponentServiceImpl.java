package com.shilko.ru.witcher.serviceimpl;

import com.shilko.ru.witcher.entity.CategoryComponent;
import com.shilko.ru.witcher.entity.Component;
import com.shilko.ru.witcher.entity.CraftOrAlchemy;
import com.shilko.ru.witcher.entity.Image;
import com.shilko.ru.witcher.repository.CategoryComponentCrudRepository;
import com.shilko.ru.witcher.repository.ComponentCrudRepository;
import com.shilko.ru.witcher.repository.CraftOrAlchemyCrudRepository;
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
    private CraftOrAlchemyCrudRepository craftOrAlchemyCrudRepository;

    @Autowired
    private ImageCrudRepository imageCrudRepository;

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
    public Optional<CraftOrAlchemy> getCraftOrAlchemyByIsAlchemy(boolean isAlchemy) {
        return craftOrAlchemyCrudRepository.findByIsAlchemy(isAlchemy);
    }

    @Override
    public void saveComponent(Component component, Image image, CraftOrAlchemy craftOrAlchemy) {
        componentCrudRepository.save(component);
        imageCrudRepository.save(image);
        craftOrAlchemyCrudRepository.save(craftOrAlchemy);
    }

    @Override
    public void saveCategoryComponent(CategoryComponent categoryComponent) {
        categoryComponentCrudRepository.save(categoryComponent);
    }
}
