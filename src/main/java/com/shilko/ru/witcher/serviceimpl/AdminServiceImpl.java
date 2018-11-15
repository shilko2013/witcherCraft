package com.shilko.ru.witcher.serviceimpl;

import com.shilko.ru.witcher.entity.UserStatusEnum;
import com.shilko.ru.witcher.entity.Users;
import com.shilko.ru.witcher.repository.UsersCrudRepository;
import com.shilko.ru.witcher.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    UsersCrudRepository usersCrudRepository;

    @Autowired
    SessionRegistry sessionRegistry;

    @Override
    public List<Users> getAllUsers() {
        return usersCrudRepository.findAll();
    }

    @Override
    public boolean setRole(String username, String role) {
        Optional<Users> user = usersCrudRepository.findByUsername(username);
        if (!user.isPresent())
            return false;
        if (!(role.equals("reader")
                || role.equals("editor")
                || role.equals("admin")))
            return false;
        Optional<Users> oldUser = usersCrudRepository.findByUsername(user.get().getUsername());
        switch (role) {
            case "reader":
                user.get().setUserStatus(UserStatusEnum.USER_STATUS_READER);
                break;
            case "editor":
                user.get().setUserStatus(UserStatusEnum.USER_STATUS_EDITOR);
                break;
            case "admin":
                user.get().setUserStatus(UserStatusEnum.USER_STATUS_ADMIN);
                break;
                default:
                    return false;
        }
        if (!oldUser.isPresent())
            return false;
        if (oldUser.get().getUserStatus() == user.get().getUserStatus())
            return true;
        usersCrudRepository.save(user.get());
        return true;
    }

    @Override
    public boolean disableSession(String username) {
        Optional<Users> user = usersCrudRepository.findByUsername(username);
        if (!user.isPresent())
            return false;
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof User) {
                UserDetails userDetails = (UserDetails) principal;
                if (userDetails.getUsername().equals(user.get().getUsername())) {
                    for (SessionInformation information : sessionRegistry.getAllSessions(userDetails, true)) {
                        information.expireNow();
                    }
                }
            }
        }
        return true;
    }
}
