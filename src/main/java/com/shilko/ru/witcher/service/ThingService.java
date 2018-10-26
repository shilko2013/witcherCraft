package com.shilko.ru.witcher.service;

public interface ThingService {
  
  //check on editing:
  boolean isThingEdited(Thing thisThing);
  
  //adding:
  void addThing(Thing newThing);
  
  //edditing:
  void editThing(Long thingID);
  
  void editThing(String thingName);
  
  void removeThing(Long thingID);
  
  void removeThing(String thingName);
  
  //getters:
  Thing getThing(Long id);
  
  Thign getThing(String thingName);
  
  //what kind is this thing???  
  
}
