package com.shilko.ru.witcher.service;

public interface ThingService {
  
  //adding:
  //arg -> ???
  void addNewThing(Thing thing);
  
  //edditing:
  void editThing(Long whichThing, Thing thisThing);
  
  //getters:
  Thing getThingById(Long id);
  Thign getThingByName(String thingName);
  //some list or map here:
  //<Thing> getAllThings();
  
}
