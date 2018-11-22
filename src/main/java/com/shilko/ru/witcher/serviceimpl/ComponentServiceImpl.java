package com.shilko.ru.witcher.serviceimpl;

import com.shilko.ru.witcher.entity.Component;
import com.shilko.ru.witcher.entity.Image;
import com.shilko.ru.witcher.repository.ComponentCrudRepository;
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
    private ImageCrudRepository imageCrudRepository;

    @Override
    public List<Component> getAllComponents() {
        return componentCrudRepository.findAll();
    }

    @Override
    public Optional<Image> getImageByIdComponent(Long id) {
        return imageCrudRepository.findByComponent_Id(id);
    }
}
