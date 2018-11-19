package com.shilko.ru.witcher.serviceimpl;

import com.shilko.ru.witcher.entity.UserStatusEnum;
import com.shilko.ru.witcher.entity.Users;
import com.shilko.ru.witcher.repository.UsersCrudRepository;
import com.shilko.ru.witcher.service.AdminService;
import com.shilko.ru.witcher.service.NotificationMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.core.session.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UsersCrudRepository usersCrudRepository;

    @Autowired
    private NotificationMailService notificationMailService;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public List<Users> getAllUsers() {
        return usersCrudRepository.findAll();
    }

    @Override
    public Pair<Boolean,Boolean> setRole(String username, String role) {
        Optional<Users> user = usersCrudRepository.findByUsername(username);
        if (!user.isPresent())
            return Pair.of(false,false);
        if (!(role.equals("reader")
                || role.equals("editor")
                || role.equals("admin")))
            return Pair.of(false,false);
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
                    return Pair.of(false,false);
        }
        if (!oldUser.isPresent())
            return Pair.of(false,false);
        if (oldUser.get().getUserStatus() == user.get().getUserStatus())
            return Pair.of(false,true);
        usersCrudRepository.save(user.get());
        return Pair.of(false,notificationMailService.sendMessage("Now your role is "+role,user.get().getEmail()));
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
