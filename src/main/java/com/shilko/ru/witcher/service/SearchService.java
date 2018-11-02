package com.shilko.ru.witcher.service;

public interface SearchService {
  
  //what type return this method???
  //generate new page with finded results...
  void searchByLine(String inputLine);
  
  void saveInputedLine(String inputLine);
  
  void suggestLike(String inputLine);
  
}
