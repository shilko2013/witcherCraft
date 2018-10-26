package com.shilko.ru.witcher.service;

public interface ComponentService {
  
  //check on editing:
  //these 2 methods are not important!!!
  boolean isComponentEdited(Component thisComponent);
  
  String getDataOfEditing(/*args -> ???*/);
  //
  
  //check on craft:
  boolean forCraft(Component thisComponent);

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

}
