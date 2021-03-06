package com.shilko.ru.witcher.serviceimpl;

import com.shilko.ru.witcher.entity.UserStatusEnum;
import com.shilko.ru.witcher.entity.Users;
import com.shilko.ru.witcher.repository.UsersCrudRepository;
import com.shilko.ru.witcher.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersCrudRepository usersCrudRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void save(Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUserStatus(UserStatusEnum.USER_STATUS_READER);
        usersCrudRepository.save(user);
    }

    @Override
    public Users findByUsername(String username) {
        return usersCrudRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Users findByEmail(String email) {
        return usersCrudRepository.findByEmail(email).orElse(null);
    }
}
