package com.shilko.ru.witcher.service;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
