package com.shilko.ru.witcher.repository;

import com.shilko.ru.witcher.entity.UserStatus;
import com.shilko.ru.witcher.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface User status crud repository.
 */
@Repository
public interface UserStatusCrudRepository extends CrudRepository<UserStatus, Long> {

    Optional<UserStatus> findById(Long id);

    @Override
    UserStatus save(UserStatus userStatus);

    @Override
    void deleteById(Long id);

    @Override
    List<UserStatus> findAll();

    UserStatus findByStatus(String status);
}
