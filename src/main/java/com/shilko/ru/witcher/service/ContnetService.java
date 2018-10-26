package com.shilko.ru.witcher.service;

public interface ContentService {
  
  //check on permissions:
  boolean isPermissionDenied(/*args -> Users and Admins*/);
  
  //only for admins:
  void addContent(String newContent);
  
  //only for logged in users and admins:
  void editContent(String editedContent);
  
  //only for admins:
  void removeContent(/*args -> ???*/);
  
}
