package com.shilko.ru.witcher.service;

public interface ThingService {
  
  //adding:
  void addNewThing(Thing newThing);
  
  //edditing:
  void editThing(Long thingID);
  
  void editThing(String thingName);
  
  void removeThing(Long thingID);
  
  void removeThing(String thingName);
  
  //getters:
  Thing getThingById(Long id);
  Thign getThingByName(String thingName);
  
  //what kind is this thing???
  
}
