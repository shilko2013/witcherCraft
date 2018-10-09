package com.shilko.ru.wither.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersCrudRepository extends CrudRepository<Users, Long> {

    @Override
    Users save(Users users);

    @Override
    Optional<Users> findById(Long id);

    @Override
    void deleteById(Long id);

    Optional<Users> findByEmail(String email);

    Optional<Users> findByLogin(String login);

    @Override
    List<Users> findAll();

}
