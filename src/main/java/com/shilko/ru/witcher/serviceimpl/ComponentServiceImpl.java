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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ComponentServiceImpl implements ComponentService {

    private static final long MAX_FILE_SIZE = 0xA00000; //10Mb

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
        componentCrudRepository.save(component);
        if (image != null) {
            image.setComponent(component);
            imageCrudRepository.save(image);
        }
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
                                               String category,
                                               boolean isAlchemy,
                                               MultipartFile imageFile) {
        DescriptionComponent descriptionComponent = new DescriptionComponent(description);
        if (!categoryComponentCrudRepository.findByName(category).isPresent())
            saveCategoryComponent(new CategoryComponent(category, ""));
        Optional<CategoryComponent> categoryComponent = categoryComponentCrudRepository.findByName(category);
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
    public ResponseEntity<String> addCategoryComponent(String name, String information) {
        if (name == null || information == null)
            return ResponseEntity.badRequest().body("Illegal set of arguments");
        try {
            if (getCategoryComponentByName(name).isPresent())
                return ResponseEntity.badRequest().body("This category already exists");
            saveCategoryComponent(new CategoryComponent(name, information));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal set of arguments");
        }
        return ResponseEntity.ok("Successfully added");
    }

    @Override
    public ResponseEntity<String> editComponent(String name,
                                                int price,
                                                double weight,
                                                String description,
                                                String category,
                                                MultipartFile imageFile) {
        try {
            Component component = componentCrudRepository.findByName(name).get();
            componentCrudRepository.save(component);
            DescriptionComponent descriptionComponent = descriptionComponentCrudRepository.findByComponent(component).get();
            component.setPrice(price);
            component.setWeight(weight);
            descriptionComponent.setDescription(description);
            if (!categoryComponentCrudRepository.findByName(category).isPresent())
                saveCategoryComponent(new CategoryComponent(category, ""));
            component.setCategory(categoryComponentCrudRepository.findByName(category).get());
            Image oldImage = imageCrudRepository.findByComponent(component).orElse(null);
            Image image = new Image();
            String[] split;
            if (imageFile == null || imageFile.getSize() == 0)
                image = oldImage;
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
            /*oldImage.setType(image.getType());
            oldImage.setPicture(image.getPicture());*/
            descriptionComponentCrudRepository.save(descriptionComponent);
            if (image != oldImage)
                imageCrudRepository.save(image);
            componentCrudRepository.save(component);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal set of arguments");
        }
        return ResponseEntity.ok("Success edit");
    }

    @Override
    public ResponseEntity<String> editCategoryComponent(String name, String information) {
        try {
            CategoryComponent categoryComponent = categoryComponentCrudRepository.findByName(name).get();
            categoryComponent.setInformation(information);
            categoryComponentCrudRepository.save(categoryComponent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Illegal set of arguments");
        }
        return ResponseEntity.ok("Success edit");
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
        componentCrudRepository.delete(component.get());
        if (component.get().getImage() != null)
            imageCrudRepository.delete(component.get().getImage());
        descriptionComponentCrudRepository.delete(component.get().getDescriptionComponent());
    }

}
