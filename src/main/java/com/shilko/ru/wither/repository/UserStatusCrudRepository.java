package com.shilko.ru.wither.repository;

import com.shilko.ru.wither.entity.UserStatus;
import com.shilko.ru.wither.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserStatusCrudRepository extends CrudRepository<UserStatus, Long> {

    Optional<UserStatus> findById(Long id);

    @Override
    UserStatus save(UserStatus userStatus);

    @Override
    void deleteById(Long id);

    @Override
    List<UserStatus> findAll();

    UserStatus findByUsers(Users users);
}
