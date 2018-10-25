package com.shilko.ru.witcher.service;

public interface ThingService {
  
  //adding:
  void addThing(Thing newThing);
  
  //edditing:
  void editThing(Long thingID);
  
  void editThing(String thingName);
  
  void removeThing(Long thingID);
  
  void removeThing(String thingName);
  
  //getters:
  Thing getThingByID(Long id);
  Thign getThingByName(String thingName);
  
  //what kind is this thing???  
}
