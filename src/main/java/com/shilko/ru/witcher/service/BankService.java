package com.shilko.ru.witcher.service;

public interface BankService {
  Long MINamountMONEY;
  
  void takeMoney(Long money);
  
  boolean checkAmountMoney(Long money);
  
  void doTransaction();
  
  String sayThanks();
  
}
