package com.shilko.ru.witcher.service;

import com.shilko.ru.witcher.entity.Users;

public interface UserService {

    void addUser(Users newUser);
    
    void editUser(Users editedUser);
    
    void removeUser(Users whichUser);
    
    void logIN(Users user);

    Users findByLogin(String login);

    Users findByEmail(String email);
    
}
