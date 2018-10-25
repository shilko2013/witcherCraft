package com.shilko.ru.witcher.service;

public interface ComponentService {

  //additing:
  void addComponent(Component newComponent);
  
  //edditing:
  void editComponent(Long componentID);
  
  void editComponent(String componentName);
  
  void removeComponent(Long componentID);
  
  void removeComponent(String componentName);
  
  //getters:
  Component getComponentByID(Long id);
  
  Component getComponentByName(String name);

  //what kind is this component???
}
