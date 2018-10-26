package com.shilko.ru.witcher.service;

public interface NotificationMailService {
  
  void mailto(String email, String message);
  
  void mailto(String email, String theme, String message);
     
}
