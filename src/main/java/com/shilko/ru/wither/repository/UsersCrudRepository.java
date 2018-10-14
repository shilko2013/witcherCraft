package com.shilko.ru.wither.repository;

import com.shilko.ru.wither.entity.UserStatus;
import com.shilko.ru.wither.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Users crud repository.
 */
@Repository
public interface UsersCrudRepository extends CrudRepository<Users, Long> {

    @Override
    Users save(Users users);

    @Override
    Optional<Users> findById(Long id);

    @Override
    void deleteById(Long id);

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<Users> findByEmail(String email);

    /**
     * Find by login optional.
     *
     * @param login the login
     * @return the optional
     */
    Optional<Users> findByLogin(String login);

    @Override
    List<Users> findAll();

    /**
     * Find all by user status list.
     *
     * @param userStatus the user status
     * @return the list
     */
    List<Users> findAllByUserStatus(UserStatus userStatus);
}
