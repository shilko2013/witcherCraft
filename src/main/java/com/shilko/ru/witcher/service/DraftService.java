package com.shilko.ru.witcher.service;

public interface DraftService {
  
  //check on draft:
  boolean isComponentByDraft(Component component);
  boolean isThingByDraft(Thing thing);  
  
  //check on whether this draft was carried out earlier with this thing and component:
  boolean isDrafted(Component component, Thing thing);
  
  void draft(Component whichComponent, Thing whichThing);
  
  void getResult();
  
  void saveDraftResult();
  
}
