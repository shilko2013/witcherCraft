package com.shilko.ru.witcher.serviceimpl;

import com.shilko.ru.witcher.config.ApplicationConfigController;
import com.shilko.ru.witcher.entity.CategoryComponent;
import com.shilko.ru.witcher.entity.Component;
import com.shilko.ru.witcher.entity.DescriptionComponent;
import com.shilko.ru.witcher.entity.Image;
import com.shilko.ru.witcher.repository.CategoryComponentCrudRepository;
import com.shilko.ru.witcher.repository.ComponentCrudRepository;
import com.shilko.ru.witcher.repository.DescriptionComponentCrudRepository;
import com.shilko.ru.witcher.repository.ImageCrudRepository;
import com.shilko.ru.witcher.service.ComponentService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ComponentServiceImpl implements ComponentService {

    private static final long MAX_FILE_SIZE = ApplicationConfigController.getMaxFileSize();

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
        if (image != null)
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

    @Override
    public Optional<Component> getComponentByName(String name) {
        return componentCrudRepository.findByName(name);
    }

    @Override
    public ResponseEntity<String> addComponent(String name,
                                               int price,
                                               double weight,
                                               String description,
                                               long categoryId,
                                               boolean isAlchemy,
                                               MultipartFile imageFile) {
        DescriptionComponent descriptionComponent = new DescriptionComponent(description);
        Optional<CategoryComponent> categoryComponent = getCategoryComponentById(categoryId);
        if (!categoryComponent.isPresent())
            return ResponseEntity.badRequest().body("Illegal category id");
        Image image = new Image();
        String[] split;
        if (imageFile == null || imageFile.getSize() == 0)
            image = null;
        else {
            if (imageFile.getSize() > MAX_FILE_SIZE)
                return ResponseEntity.badRequest().body("So big image file, maximum size is " + (MAX_FILE_SIZE >> 20) + "Mb");
            try {
                split = imageFile.getOriginalFilename().split("\\.");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Illegal image file");
            }
            String imageType = split[split.length - 1];
            image.setType(imageType);
            if (Image.getMediaType(image) == MediaType.ALL)
                return ResponseEntity.badRequest().body("Illegal image file extension. Server supports png, jpeg, jpg and gif images.");
            try {
                image.setPicture(Base64.encodeBase64String(imageFile.getBytes()));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Illegal image file");
            }
        }
        Component component = new Component(name, price, weight, descriptionComponent, categoryComponent.get(), isAlchemy, image);
        try {
            saveComponent(component, image, descriptionComponent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal set of arguments");
        }
        return ResponseEntity.ok("Successfully added");
    }

    @Override
    public
    ResponseEntity<String> addCategoryComponent(String name, String information, boolean add) {
        if (name == null || information == null)
            return ResponseEntity.badRequest().body("Illegal set of arguments");
        try {
            if (add && getCategoryComponentByName(name).isPresent())
                return ResponseEntity.badRequest().body("This category already exists");
            saveCategoryComponent(new CategoryComponent(name, information));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal set of arguments");
        }
        return ResponseEntity.ok("Successfully added");
    }

    @Override
    public ResponseEntity<String> deleteCategoryComponent(Long id) {
        if (!componentCrudRepository.findAllByCategoryComponentEquals(categoryComponentCrudRepository.findById(id).get()).isEmpty())
            return ResponseEntity.badRequest().body("Can not delete - component with this category exists");
        categoryComponentCrudRepository.deleteById(id);
        return ResponseEntity.ok("Category deleted");
    }

    @Override
    public void deleteComponent(Long id) {
        Optional<Component> component = componentCrudRepository.findById(id);
        imageCrudRepository.delete(component.get().getImage());
        descriptionComponentCrudRepository.delete(component.get().getDescriptionComponent());
        componentCrudRepository.delete(component.get());
    }

}
