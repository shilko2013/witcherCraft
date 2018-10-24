package com.shilko.ru.witcher.service;

public interface ComponentService {

  //additing:
  void addNewComponent(Component newComponent);
  
  //edditing:
  void editComponent(Long whichComponent, Component thisComponent);
  
  //getters:
  Component getComponentById(Long id);
  Component getComponentByName(String name);
  //here a list or map
  //<Component> getAllComponents();

}
