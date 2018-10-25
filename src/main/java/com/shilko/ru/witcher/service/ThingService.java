package com.shilko.ru.witcher.service;

public interface ThingService {
  
  //adding:
  void addNewThing(Thing newThing);
  
  //edditing:
  void editThing(Long whichThing, Thing thisThing);
  
  //getters:
  Thing getThingById(Long id);
  Thign getThingByName(String thingName);
  //some list or map here:
  //<Thing> getAllThings();
  
  //what kind is this thing???
}
