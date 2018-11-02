package com.shilko.ru.witcher.service;

public interface ThingService {
  
  //check on editing:
  //These 2 methods are not important!!!
  boolean isThingEdited(Thing thisThing);
  
  String getDataOfEditing(/*args -> ???*/);
  //
  
  //check on craft:
  boolean forCraft(Thing thisThing);
  
  //adding:
  void addThing(Thing newThing);
  
  //edditing:
  void editThing(Long thingID);
  
  void editThing(String thingName);
  
  void removeThing(Long thingID);
  
  void removeThing(String thingName);
  
  //getters:
  Thing getThing(Long id);
  
  Thing getThing(String thingName);
  
}
