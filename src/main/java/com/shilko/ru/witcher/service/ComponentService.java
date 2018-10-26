package com.shilko.ru.witcher.service;

public interface ComponentService {
  
  //check on editing:
  boolean isComponentEdited(Component thisComponent);
  
  String getDataOfEditing(/*args -> ???*/);

  //additing:
  void addComponent(Component newComponent);
  
  //edditing:
  void editComponent(Long componentID);
  
  void editComponent(String componentName);
  
  void removeComponent(Long componentID);
  
  void removeComponent(String componentName);
  
  //getters:
  Component getComponent(Long id);
  
  Component getComponent(String name);

  //what kind is this component???
  
}
