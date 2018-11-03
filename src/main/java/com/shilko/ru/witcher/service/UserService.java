package com.shilko.ru.witcher.service;

import com.shilko.ru.witcher.entity.Users;

public interface UserService {

    void save(Users user);

    Users findByUsername(String username);

    Users findByEmail(String email);
}
