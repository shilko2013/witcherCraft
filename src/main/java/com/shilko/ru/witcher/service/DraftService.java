package com.shilko.ru.witcher.service;

public interface DraftService {
  
  //What does this method return???
  // draft(Component whichComponent, Thing whichThing);
  
  //check on draft:
  boolean isComponentByDraft(Component component);
  boolean isThingByDraft(Thing thing);  
  
  //add these two methods:
  
  void saveDraftResult();
  
  void mailtoDraftResult();
}
