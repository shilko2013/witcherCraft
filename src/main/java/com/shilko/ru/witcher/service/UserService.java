package com.shilko.ru.witcher.service;

import com.shilko.ru.witcher.entity.Users;

public interface UserService {

    void addUser(Users users);

    Users findByLogin(String login);

    Users findByEmail(String email);
    
    void changePassword(String newPassword);
}
