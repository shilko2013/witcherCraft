package com.shilko.ru.witcher.service;

import com.shilko.ru.witcher.entity.Users;

import java.util.List;

public interface AdminService {

    List<Users> getAllUsers();

    boolean setRole(String username, String role);

    boolean disableSession(String username);
}
