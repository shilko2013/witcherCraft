package com.shilko.ru.witcher.service;

import com.shilko.ru.witcher.entity.CategoryComponent;
import com.shilko.ru.witcher.entity.Component;
import com.shilko.ru.witcher.entity.DescriptionComponent;
import com.shilko.ru.witcher.entity.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ComponentService {

    List<Component> getAllComponents(boolean isAlchemy);

    Optional<Image> getImageByIdComponent(Long id);

    Optional<CategoryComponent> getCategoryComponentById(Long id);

    void saveComponent(Component component, Image image, DescriptionComponent descriptionComponent);

    void saveCategoryComponent(CategoryComponent categoryComponent);

    Optional<CategoryComponent> getCategoryComponentByName(String name);

    Optional<Component> getComponentById(Long id);

    Optional<Component> getComponentByName(String name);

    ResponseEntity<String> addComponent(String name,
                                        int price,
                                        double weight,
                                        String description,
                                        String category,
                                        boolean isAlchemy,
                                        MultipartFile imageFile);

    ResponseEntity<String> addCategoryComponent(String name, String information);

    ResponseEntity<String> editComponent(String name,
                                         int price,
                                         double weight,
                                         String description,
                                         String category,
                                         MultipartFile imageFile);

    ResponseEntity<String> editCategoryComponent(String name, String information);

    ResponseEntity<String> deleteCategoryComponent(Long id);

    void deleteComponent(Long id);
}
