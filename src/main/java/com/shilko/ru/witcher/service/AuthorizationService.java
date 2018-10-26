package com.shilko.ru.witcher.service;

public interface AuthorizationService {
 
 signInByLogin(String login, String password);
 
 signInByEmail(Stirng email, String password);
 
 signUP(String email, String login, String password);
 
}
