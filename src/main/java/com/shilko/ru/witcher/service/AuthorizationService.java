package com.shilko.ru.witcher.service;

import com.shilko.ru.witcher.entity.Users;

public interface AuthorizationService {
 
 Users signInByLogin(String login, String password);
 
 Users signInByEmail(String email, String password);
 
 Users signIN(String email, String login, String password);
 
 Users signUP(String email, String login, String password);
 
}
