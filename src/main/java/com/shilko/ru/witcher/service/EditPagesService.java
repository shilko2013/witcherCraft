package com.shilko.ru.witcher.service;

public interface PagesEditingService {
  
  void addNewPage(String content);
  
  void editPageContent(String newContent);
  
  //This feature can only be used by an administrator(s)... 
  void confirmEditing();
}
