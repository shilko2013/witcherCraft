package com.shilko.ru.witcher.service;

import com.shilko.ru.witcher.entity.Users;
import org.springframework.data.util.Pair;

import java.util.List;

public interface AdminService {

    List<Users> getAllUsers();

    /*
    @return first arg - success editing, second - success sending mail, <false,true> - role has already set
    */
    Pair<Boolean,Boolean> setRole(String username, String role);

    boolean disableSession(String username);
}
