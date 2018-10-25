package com.shilko.ru.witcher.service;

public interface EditPagesService {
  
  //only for admins:
  void addContent(String newContent);
  
  //only for logged in users and admins:
  void editContent(String editedContent);
  
}
